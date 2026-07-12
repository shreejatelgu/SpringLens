# SpringLens — System Design Document

**Version:** 1.0
**Status:** Development Baseline
**Owner:** SpringLens Engineering
**Architecture:** Project Knowledge Graph (PKG)

---

## Table of Contents

1. Executive Summary
2. System Overview
3. Architectural Principles
4. High-Level Architecture
5. Project Knowledge Graph (PKG) — Data Model
6. Project Import Engine
7. Discovery & Parsing Engine
8. Knowledge Graph Builder
9. Analysis Engine
10. Diagram Generation Engine
11. Playback Engine
12. AI Architecture
13. Backend Architecture
14. Frontend Architecture
15. Database Design
16. API Design
17. Security Architecture
18. Deployment Architecture
19. Scalability & Performance
20. Logging & Monitoring
21. Future Architecture
22. Trade-offs Summary

---

## 1. Executive Summary

SpringLens is a developer intelligence platform that helps engineers understand unfamiliar Spring Boot applications through static code analysis, interactive visualization, and contextual AI assistance. It targets a common, expensive problem: a developer joins a project, opens a repository with hundreds of classes, and has no fast way to see how the pieces fit together — how beans wire together, which classes expose which endpoints, how a request actually flows from controller to repository.

Rather than solving this per-feature (one parser for a dependency graph, another for security scanning, another for the AI assistant), SpringLens parses a project exactly once and normalizes it into a single semantic model — the **Project Knowledge Graph (PKG)**. Every capability in the platform — diagrams, playback animations, security checks, and AI explanations — reads from that one graph.

**Engineering philosophy:** *Parse once. Understand everywhere.*

This document is the technical counterpart to the product requirements: it defines how SpringLens is built, not what it should do. It is the reference contributors use when implementing or extending any subsystem.

---

## 2. System Overview

### 2.1 What SpringLens Does

A user uploads a Spring Boot project (ZIP archive or public GitHub URL). SpringLens validates it, statically analyzes every Java source file, builds a knowledge graph of the project's structure, and exposes that graph through:

- **Architecture Explorer** — interactive, filterable diagrams of packages, classes, and layers
- **Dependency Explorer** — bean dependency graphs and coupling analysis
- **API Explorer** — REST endpoint inventory mapped to controllers/services/repositories
- **Security Analyzer** — static checks against common Spring misconfiguration patterns
- **Playback Engine** — step-by-step animated walkthroughs of DI wiring and request flow
- **AI Chat** — natural-language Q&A grounded in the graph, not raw source

### 2.2 Core Engineering Philosophy

| Principle | Description |
|---|---|
| Parse Once | Every Java file is analyzed exactly once per project version; no subsystem re-parses. |
| Build Once | The PKG is constructed once during analysis and treated as immutable afterward. |
| Explain Everything | No diagram or relationship should be left unexplained; AI augments, never replaces, deterministic output. |
| Interactive First | Every visualization supports inspection, filtering, and navigation — not static images. |
| AI Last | AI is the final layer. It explains verified knowledge; it never performs primary analysis. |

### 2.3 End-to-End Lifecycle

```
Project Upload
   │
   ▼
Validation           (structure, Maven, Spring Boot compatibility)
   │
   ▼
Discovery             (locate packages, classes, resources, config, tests)
   │
   ▼
Semantic Parsing      (JavaParser: classes, methods, annotations, beans, endpoints)
   │
   ▼
PKG Construction      (normalize metadata into nodes + edges)
   │
   ▼
Analysis Engines      (architecture, dependency, security, API, entity — PKG only)
   │
   ▼
Interactive Dashboard (diagrams, playback, AI chat)
```

Each stage is independent and communicates only through well-defined interfaces — never by reaching into another stage's internals.

### 2.4 System Context Diagram

```
                     Developer
                         │
                         ▼
              SpringLens Web Application
                         │
                         ▼
              Spring Boot REST Backend
                         │
   ┌─────────────────────┼─────────────────────┐
   ▼                     ▼                     ▼
Project Import     Analysis Pipeline      AI Assistant
   │                     │                     │
   ▼                     ▼                     ▼
ZIP / GitHub    Project Knowledge Graph   Context Retrieval
                         │
   ┌───────────┬─────────┼─────────┬────────────┐
   ▼           ▼         ▼         ▼            ▼
Architecture Dependency  API    Security     Playback
 Explorer     Explorer  Explorer Analyzer     Engine
   └───────────┴─────────┼─────────┴────────────┘
                         ▼
              Interactive React Frontend
```

### 2.5 Major Subsystems

| Subsystem | Responsibility | Reads Source? | Reads PKG? |
|---|---|---|---|
| Project Import Engine | Validate and ingest uploaded project | No | No |
| Discovery Engine | Locate analyzable assets | Yes | No |
| Parser Engine | Extract classes, methods, annotations, dependencies via JavaParser | Yes | No |
| PKG Builder | Normalize extracted metadata into graph nodes/edges | No | Writes |
| Analysis Engine | Architecture, dependency, security, API, entity checks | No | Yes |
| Diagram Engine | Render interactive diagrams from the PKG | No | Yes |
| Playback Engine | Animate DI wiring and request-flow behavior | No | Yes |
| AI Engine | Generate contextual explanations | No | Yes |
| Web Platform | Frontend UX, auth, orchestration via REST | No | Via API |

Only Discovery and Parser touch source files. Every other subsystem is downstream of the PKG. This boundary is the single most important constraint in the system — it's what keeps every other engine independently testable and replaceable.

### 2.6 Architectural Boundaries

**In scope:** parsing projects, building semantic models, generating diagrams, explaining architecture, visualizing relationships, static security inspection, educational playback.

**Explicitly out of scope:** code editing, compilation, runtime debugging, build automation, CI/CD, source control. SpringLens complements the IDE; it does not replace it.

### 2.7 System Layers

```
Presentation Layer    → React, React Flow, Tailwind, Playback UI
Application Layer     → REST APIs, Authentication, Services
Analysis Layer        → Parser, PKG Builder, Analysis Engines
Knowledge Layer       → Project Knowledge Graph (PKG)
Infrastructure Layer  → PostgreSQL, File Storage, AI Provider
```

Dependencies flow strictly downward. Presentation never reaches Infrastructure directly. The Knowledge Layer contains no UI logic. Infrastructure contains no business logic.

---

## 3. Architectural Principles

These are constraints that every subsystem in this document must comply with — not aspirational guidelines.

### 3.1 Single Source of Truth
The PKG is the only authoritative representation of an analyzed project. No subsystem maintains an independent model of the project. If the Security Engine needs to know about a class's annotations, it queries the PKG — it does not re-read the `.java` file.

### 3.2 Parse Once
Semantic parsing happens exactly once per project version. After the PKG exists, the Architecture Explorer, Dependency Graph, Security Analyzer, and AI Assistant all query the graph — none of them re-parse Java files.

### 3.3 Deterministic Analysis First
Diagram generation, dependency analysis, and architectural discovery are performed entirely through static analysis. AI is used only for explanations, summaries, and natural-language interaction — never for producing the underlying facts. This keeps core outputs reproducible: running the same project through SpringLens twice always produces the same diagram.

### 3.4 Separation of Concerns
Each engine has one job:

- Parser Engine: extract metadata — never generates diagrams
- Diagram Engine: generate diagrams — never parses Java
- AI Engine: explain diagrams — never builds diagrams

### 3.5 Loose Coupling
Engines communicate only through the PKG, never directly with each other.

```
Parser → PKG Interface → Diagram Engine       (correct)
Parser → Diagram Engine → Security Engine → AI  (avoided)
```

### 3.6 High Cohesion
Each engine contains only closely related functionality. The Parser Engine handles AST parsing, annotation extraction, and dependency discovery — it does not make AI calls or render UI.

### 3.7 Dependency Direction
```
Frontend → REST API → Application Services → Analysis Engine → PKG → Database
```
Never reversed. The database never calls the parser; the parser never knows the frontend exists.

### 3.8 Stateless Backend
REST services are stateless. Each request carries enough information to be handled independently; project state lives only in PostgreSQL and file storage. This is what makes horizontal scaling possible.

### 3.9 Immutable Analysis Results
Once built, a PKG is treated as immutable. If a project's source changes, the old PKG is discarded and a new one is built from scratch rather than patched in place — this guarantees every consumer of the graph sees a consistent snapshot.

### 3.10 Progressive Disclosure
Large systems are never dumped on the user at once. SpringLens reveals complexity in layers:

```
Application → Modules → Packages → Classes → Methods
```

### 3.11 Explainability
Every generated artifact should be able to answer both "what is this" and "why does it exist" — a dependency edge should be traceable back to the source location that created it; a security warning should come with a rationale.

### 3.12 Fail Gracefully
Failure in one subsystem must not cascade. If the AI Gateway is offline, the Architecture Explorer and Dependency Graph still work. If the Security Engine throws, it does not take down diagram rendering.

### 3.13 Performance by Design
Parse once, cache the PKG, lazily load diagram data, render incrementally, and traverse the graph efficiently. Performance is treated as an architectural requirement, not a later optimization pass.

### 3.14 Security by Design
Uploaded code is never executed. Archives are validated on upload, file paths are sanitized against traversal attacks, upload size is capped, and temporary workspaces are isolated per analysis job and deleted afterward. No uploaded project is trusted by default.

---

## 4. High-Level Architecture

### 4.1 Container View

```
┌──────────────────────────────┐
│         React Frontend       │
│───────────────────────────── │
│ Dashboard                    │
│ Diagram Explorer             │
│ Playback Viewer              │
│ AI Chat                      │
└──────────────┬───────────────┘
               │ REST (JSON over HTTPS)
               ▼
┌──────────────────────────────┐
│       Spring Boot API        │
│───────────────────────────── │
│ Authentication (JWT)         │
│ Project APIs                 │
│ Analysis APIs                │
│ AI APIs                      │
└──────────────┬───────────────┘
               │
   ┌───────────┼────────────────┐
   ▼            ▼                ▼
Analysis    Diagram          AI Gateway
Engine      Engine
   │            │                │
   └────────────┼────────────────┘
                ▼
      Project Knowledge Graph
                │
                ▼
          PostgreSQL Storage
```

Each container has exactly one primary responsibility; no container owns business logic outside its own domain.

### 4.2 Component Communication Model

Communication within the backend is strictly request–response. There is no direct engine-to-engine channel; the PKG is the integration layer.

```
Parser → PKG Builder → Project Knowledge Graph → { Diagram, Security, Playback, AI }
```

Adding a future Metrics Engine or Documentation Engine only requires a new PKG consumer — none of the existing engines need to change.

---

## 5. Project Knowledge Graph (PKG) — Data Model

The PKG is the semantic backbone of the entire platform. Every capability in SpringLens exists because this graph exists.

### 5.1 Conceptual Model

The PKG is a directed, typed, attributed graph:

- **Node** — a software entity (class, interface, method, field, bean, endpoint, table, etc.)
- **Edge** — a typed relationship between two nodes (extends, implements, calls, injects, exposes, maps-to)

### 5.2 Node Types

| Node Type | Represents |
|---|---|
| `Module` | A logical grouping (e.g., a top-level package) |
| `Package` | A Java package |
| `Class` | A class, record, or enum |
| `Interface` | A Java interface |
| `Method` | A method or constructor |
| `Field` | A class field |
| `Bean` | A Spring-managed bean |
| `Endpoint` | A REST endpoint (`@GetMapping`, `@PostMapping`, etc.) |
| `Entity` | A JPA `@Entity` |
| `Table` | A database table mapped from an entity |
| `Annotation` | An annotation applied to a node |
| `Configuration` | A `@Configuration` class or property source |

### 5.3 Edge Types

| Edge Type | Meaning |
|---|---|
| `EXTENDS` | Class/interface inheritance |
| `IMPLEMENTS` | Interface implementation |
| `CALLS` | Method invocation |
| `INJECTS` | Constructor/field dependency injection |
| `EXPOSES` | Class exposes a REST endpoint |
| `MAPS_TO` | Entity maps to a database table |
| `ANNOTATED_WITH` | Node carries a given annotation |
| `DEPENDS_ON` | Package/module-level dependency (aggregated from lower-level edges) |
| `RETURNS` | Method return type relationship |
| `PARAMETER_OF` | Method parameter type relationship |

### 5.4 Example Node Schema

```json
{
  "id": "node_8f21",
  "type": "Class",
  "name": "OrderService",
  "package": "com.example.orders.service",
  "sourceFile": "OrderService.java",
  "lineRange": [12, 96],
  "annotations": ["@Service"],
  "properties": {
    "isInterface": false,
    "isAbstract": false,
    "modifiers": ["public"]
  }
}
```

### 5.5 Example Edge Schema

```json
{
  "id": "edge_2c14",
  "type": "INJECTS",
  "source": "node_8f21",
  "target": "node_9a03",
  "properties": {
    "injectionType": "constructor",
    "fieldName": "orderRepository"
  }
}
```

### 5.6 Graph Construction Rules

- Every extracted class, interface, method, field, bean, and endpoint becomes exactly one node.
- Relationships discovered during parsing (inheritance, injection, invocation, mapping) become edges.
- Nodes carry a stable `id`, source location, and raw annotation list so any downstream engine can trace a diagram element back to a specific line of code.
- The graph is built bottom-up: method-level and field-level detail first, then aggregated into class-level and package-level summary edges (used for high-level architecture diagrams so the UI isn't forced to render every method by default).

### 5.7 Indexing & Traversal

To support interactive UI queries (e.g., "show everything this bean depends on, two hops out"), the PKG Builder maintains:

- An adjacency index (outgoing/incoming edges per node) for O(1) neighbor lookups
- A type index (all nodes of type `Endpoint`, all edges of type `INJECTS`, etc.) for filtered queries
- A package-path index for progressive disclosure (module → package → class drill-down)

### 5.8 Why a Graph, Not a Flat Store

Three approaches were evaluated before settling on the graph model:

**A — Feature-oriented analysis.** Each feature parses independently. Trivial to prototype, but parsing is duplicated across features, interpretations drift, and it doesn't scale as capabilities are added.

**B — Central metadata store.** Parse once, store extracted metadata in flat tables. Removes duplicate parsing, but relationships (class → bean → endpoint → table) are expensive to reconstruct at query time and the model resists extension.

**C — Project Knowledge Graph (selected).** Parse once, build a semantic graph, let every subsystem query it directly. This gives a single source of truth, efficient relationship traversal, a natural extension point for future plugins, low AI token usage (only the relevant subgraph is sent to the model), and simpler feature implementation — a new capability becomes a graph query instead of a new parsing pipeline.

---

## 6. Project Import Engine

**Responsibility:** accept a project, validate it, and produce a clean, isolated workspace for downstream analysis.

### 6.1 Supported Inputs
- ZIP archive upload
- Public GitHub repository URL

### 6.2 Validation Steps
1. Archive integrity check (valid ZIP, size under configured limit)
2. Structural check — presence of `pom.xml` at a recognizable root
3. Maven project check — parseable `pom.xml`, Spring Boot parent/starter dependency present
4. Path sanitization — reject any archive entry that resolves outside the extraction root (zip-slip protection)

### 6.3 Failure Handling
Unsupported or malformed projects terminate the workflow immediately with a descriptive validation error (e.g., "No `pom.xml` found at project root", "Project exceeds maximum upload size of 200MB"). No partial analysis is attempted on an invalid project.

### 6.4 Workspace Isolation
Each import is extracted into a uniquely named, per-job temporary directory. This directory is never reused across jobs and is deleted once analysis completes (success or failure) so no uploaded source persists on disk longer than necessary.

---

## 7. Discovery & Parsing Engine

### 7.1 Discovery Phase
The Discovery Engine walks the validated project structure and produces an inventory of analyzable assets: Java source files (grouped by `src/main/java` vs `src/test/java`), resource files, `application.properties`/`application.yml`, and the Maven module layout. No semantic analysis happens here — this is a structural scan only, and it is intentionally cheap so a bad project fails fast before the expensive parsing step.

### 7.2 Parsing Phase
The Parser Engine processes every discovered Java file using **JavaParser**, extracting:

- Classes, interfaces, records, enums
- Methods, constructors, fields
- Annotations and their attribute values
- Field/constructor-based dependency injection
- REST endpoint mappings (`@RestController`, `@RequestMapping` and its verb-specific variants)
- Bean definitions (`@Component`, `@Service`, `@Repository`, `@Bean`)
- JPA entity/table mappings (`@Entity`, `@Table`, `@Column`, relationship annotations)

The parser produces **structured metadata only** — it never generates diagrams and never calls the AI provider. Its sole output is a stream of extracted facts consumed by the PKG Builder.

### 7.3 Parsing Constraints
- Only statically resolvable constructs are extracted — reflection-based or runtime-generated proxies are out of scope for v1.
- Parsing failures on an individual file are isolated: a malformed or unparseable file is logged and skipped rather than aborting the entire analysis run.

---

## 8. Knowledge Graph Builder

**Responsibility:** normalize the Parser Engine's structured metadata into the PKG's node/edge model (Section 5).

### 8.1 Build Pipeline
```
Parsed Metadata (per file)
   → Node Generation      (one node per class/method/field/bean/endpoint/entity)
   → Edge Generation       (inheritance, injection, calls, mappings)
   → Cross-File Resolution (link edges across file boundaries — e.g., a controller injecting a service defined in another file)
   → Aggregation           (roll method/field-level detail into class- and package-level summary edges)
   → Indexing               (adjacency, type, and package-path indexes)
   → Persistence            (write graph to PostgreSQL)
```

### 8.2 Cross-File Resolution
Because Java files are parsed independently, dependency edges initially reference types by name only. The Builder performs a resolution pass across the full symbol table for the project before finalizing edges, so an `@Autowired OrderRepository` field correctly resolves to the `OrderRepository` node defined in a different package.

### 8.3 Determinism Guarantee
Given the same source tree, the Builder always produces a graph with the same nodes, edges, and IDs. This is what makes the PKG safe to cache and safe for the AI Gateway to treat as ground truth.

---

## 9. Analysis Engine

The Analysis Engine is a family of specialized, independent analyzers that all read the PKG and nothing else.

| Analyzer | Purpose |
|---|---|
| Architecture Analyzer | Detects layering (controller/service/repository), package cohesion, module boundaries |
| Dependency Analyzer | Computes coupling, fan-in/fan-out, and cyclic dependency detection |
| Security Analyzer | Flags common static misconfigurations (see 17.2) |
| API Analyzer | Builds the full REST endpoint inventory with request/response shapes |
| Bean Analyzer | Maps the full DI graph, flags ambiguous/missing bean wiring |
| Entity Analyzer | Maps JPA entities to inferred database tables and relationships |
| Test Coverage Analyzer | Cross-references test source packages against production packages (structural presence, not runtime coverage) |

Each analyzer runs independently and writes its findings back as PKG annotations (not new node types) so the Diagram Engine and AI Gateway can surface them without a separate query path. A failure in one analyzer (e.g., Security Analyzer throws on an unusual annotation combination) does not block the others — this follows the "fail gracefully" principle from Section 3.12.

---

## 10. Diagram Generation Engine

**Responsibility:** transform PKG queries into interactive visualizations rendered by the React frontend.

### 10.1 Supported Views
- **Architecture Explorer** — package/module-level layering view with progressive drill-down into classes
- **Dependency Graph** — bean-level DI graph, filterable by package or annotation type
- **API Flow Diagram** — controller → service → repository chains per endpoint
- **Entity Relationship Diagram** — JPA entity/table relationships
- **Bean Dependency Graph** — full constructor/field injection graph

### 10.2 Rendering Strategy
The Diagram Engine does not pre-render diagrams server-side. It exposes graph-shaped API responses (nodes + edges scoped to the requested view and depth), and the frontend (React Flow) is responsible for layout and rendering. This keeps the backend stateless and lets the same PKG query power multiple visual layouts without re-computation.

### 10.3 Query Model
Diagram requests are expressed as scoped graph queries, e.g.: "all `Class` nodes in package `com.example.orders`, plus their `INJECTS` and `CALLS` edges, one hop out." This lets the frontend request exactly the subgraph it needs for progressive disclosure instead of shipping the entire PKG to the browser.

---

## 11. Playback Engine

**Responsibility:** produce animated, step-by-step visualizations of framework behavior that are otherwise invisible in a static diagram — for example, the order in which Spring wires beans, or how a request travels from `@RestController` through service and repository layers to the database.

### 11.1 Playback Model
A playback sequence is a PKG traversal ordered into discrete steps:

```
Step 1: HTTP request hits @RestController method
Step 2: Controller method delegates to injected @Service
Step 3: Service calls injected @Repository
Step 4: Repository issues query against mapped @Entity/table
Step 5: Response propagates back up the call chain
```

Each step references the exact PKG nodes/edges involved, so the frontend can highlight the corresponding diagram elements in sync with the animation.

### 11.2 Design Constraint
Playback sequences are derived entirely from static call-graph edges already present in the PKG (`CALLS`, `INJECTS`, `EXPOSES`) — the engine does not execute the uploaded application to observe real request flow. This keeps playback consistent with Section 3.14 (Security by Design: never execute uploaded code).

---

## 12. AI Architecture

### 12.1 Position in the System
AI is the last layer in the pipeline, not the first. It never performs primary analysis — it explains conclusions the deterministic engines already reached.

### 12.2 Request Flow
```
User question (AI Chat)
   → AI Gateway retrieves relevant PKG subgraph (not the full graph)
   → Contextual prompt constructed from that subgraph
   → Request sent to configured language model
   → Response returned, annotated with references back to PKG nodes
```

### 12.3 Context Retrieval
The Gateway identifies the smallest relevant subgraph for a question — e.g., "why does `OrderService` depend on `PaymentGateway`?" resolves to the specific `INJECTS`/`CALLS` edges between those two nodes, not the entire project graph. This keeps prompts small, keeps cost predictable, and keeps answers traceable to a specific PKG fact.

### 12.4 Source Code Access
Raw source code is never sent to the model by default. It is only included if a user explicitly requests source-level explanation for a specific node, and even then only the relevant method/class body is included — not the surrounding file.

### 12.5 Failure Isolation
If the AI provider is unavailable or rate-limited, every deterministic feature (Architecture Explorer, Dependency Graph, Security Analyzer, Playback) continues to function normally. The AI Chat panel degrades to a clear "assistant unavailable" state rather than blocking the rest of the UI.

---

## 13. Backend Architecture

### 13.1 Technology
Spring Boot REST backend, organized around the engine boundaries defined in Section 2.5 — each engine is a separate service-layer module with its own package, not a shared "utils" grab bag.

### 13.2 Layering Within the Backend
```
Controller Layer   → REST endpoints, request validation, DTO mapping
Service Layer      → Orchestrates engines (Import → Discovery → Parser → PKG Builder → Analysis)
Engine Layer        → Import, Discovery, Parser, PKG Builder, Analysis, Diagram, Playback, AI
Repository Layer   → PostgreSQL persistence via Spring Data JPA
```

### 13.3 Statelessness
No engine holds in-memory state across requests. A long-running analysis job is tracked via a persisted job record (status, progress, error state) so the frontend can poll or subscribe to progress without the backend needing sticky sessions — this is what allows the API layer to scale horizontally behind a load balancer.

### 13.4 Job Model
Analysis is asynchronous: the Import API returns a job ID immediately, and the Discovery → Parser → PKG Builder → Analysis pipeline runs as a background job. The frontend polls job status until the PKG is ready, then switches to synchronous graph-query endpoints for diagrams and AI chat.

---

## 14. Frontend Architecture

### 14.1 Stack
React, React Flow (diagram rendering), Tailwind CSS, a dedicated Playback UI layer for animation sequencing.

### 14.2 Structure
- **Dashboard** — project list, upload flow, job status
- **Diagram Explorer** — React Flow canvas driven by scoped PKG queries (Section 10.3); supports pan/zoom/filter and progressive drill-down
- **Playback Viewer** — step-through controls synced to Playback Engine sequences, highlighting active nodes/edges on the diagram canvas
- **AI Chat** — conversational panel that shows model responses alongside clickable references back to the diagram

### 14.3 Data Flow
The frontend never computes graph layout logic on raw source — it only ever receives pre-scoped node/edge JSON from the Diagram Engine's query endpoints and lets React Flow handle presentation. This keeps the frontend a pure consumer of the PKG, consistent with Section 3.7 (dependency direction).

---

## 15. Database Design

### 15.1 Storage Responsibilities
PostgreSQL persists: user/auth data, project metadata, job status records, and the built PKG itself (nodes and edges, indexed for the query patterns in Section 5.7).

### 15.2 Core Tables (indicative)

| Table | Purpose |
|---|---|
| `users` | Account and auth data |
| `projects` | Uploaded project metadata (name, source type, owner) |
| `analysis_jobs` | Job status, timestamps, error state per analysis run |
| `pkg_nodes` | Node records (id, type, name, properties as JSONB, source location) |
| `pkg_edges` | Edge records (id, type, source_node_id, target_node_id, properties as JSONB) |
| `analysis_findings` | Output of the Analysis Engine (Section 9), linked to node/edge IDs |

### 15.3 Indexing Strategy
- Composite index on `(project_id, node_type)` for type-filtered diagram queries
- Index on `(source_node_id)` and `(target_node_id)` on `pkg_edges` for adjacency lookups
- JSONB GIN index on node `properties` for annotation-based filtering (e.g., "find all `@Service` beans")

### 15.4 Versioning
A project's PKG is tied to a specific `analysis_job_id`, not overwritten in place — re-analyzing a project creates a new job and a new graph snapshot rather than mutating the existing one, in line with the immutability principle in Section 3.9.

---

## 16. API Design

### 16.1 Conventions
REST over HTTPS, JSON payloads, JWT-based authentication, resource-oriented URLs.

### 16.2 Representative Endpoints

| Method | Path | Purpose |
|---|---|---|
| `POST` | `/api/projects` | Upload a project (ZIP or GitHub URL) |
| `GET` | `/api/projects/{id}/jobs/{jobId}` | Poll analysis job status |
| `GET` | `/api/projects/{id}/graph?type=Class&depth=2` | Scoped PKG query for diagrams |
| `GET` | `/api/projects/{id}/architecture` | Architecture Explorer view data |
| `GET` | `/api/projects/{id}/dependencies` | Dependency graph view data |
| `GET` | `/api/projects/{id}/security-findings` | Security Analyzer results |
| `GET` | `/api/projects/{id}/playback/{sequenceId}` | Playback step sequence |
| `POST` | `/api/projects/{id}/ai/ask` | AI Chat question, PKG-grounded answer |

### 16.3 Error Model
Consistent error envelope across all endpoints (`code`, `message`, `details`) so the frontend can render validation failures (Section 6.3) and job failures with the same handling logic.

---

## 17. Security Architecture

### 17.1 Platform Security
- Uploaded code is never executed under any circumstance.
- Archive extraction is protected against zip-slip/path traversal.
- Upload size is capped at the API boundary before extraction begins.
- Each analysis job runs in an isolated temporary workspace, deleted on completion.
- Authentication via JWT; all project data is scoped to its owning user at the query layer, not just the UI layer.

### 17.2 Static Security Analysis (product feature)
The Security Analyzer (Section 9) checks the PKG for static, well-known misconfiguration patterns, e.g.:
- Overly permissive CORS configuration
- Missing authentication annotations on sensitive endpoints
- Hardcoded credentials or secrets in configuration files
- Disabled CSRF protection without a documented justification
- Entities exposing internal fields directly through REST responses

These findings are deterministic and PKG-derived — the AI layer may explain *why* a finding matters, but it never decides *what* counts as a finding.

---

## 18. Deployment Architecture

### 18.1 Topology
```
Load Balancer
     │
     ▼
Spring Boot API instances (stateless, horizontally scaled)
     │
     ▼
PostgreSQL (primary + read replica for graph-heavy read traffic)
     │
     ▼
File Storage (temporary workspaces for in-flight analysis jobs)
```

### 18.2 Environment Separation
Standard three-tier environment separation (development, staging, production), with analysis job workspaces and uploaded archives never shared across environments.

### 18.3 Externalized AI Provider
The AI Gateway calls out to an external model provider rather than hosting a model in-cluster, keeping the core platform's deployment footprint independent of AI infrastructure changes.

---

## 19. Scalability & Performance

### 19.1 Where Cost Lives
The expensive step is Discovery + Parsing + PKG Construction. Once a PKG exists, every downstream query (diagram, AI context retrieval, security findings) is a cheap indexed graph read.

### 19.2 Strategies
- **Parse once, cache the PKG** — repeated visits to a project never re-trigger analysis.
- **Scoped queries** — the Diagram Engine and AI Gateway always request bounded subgraphs (Section 5.7, 10.3, 12.3), never the full graph, keeping response payloads and prompt sizes predictable regardless of project size.
- **Asynchronous analysis jobs** — large projects don't block the API thread pool; the frontend polls or subscribes to job status.
- **Horizontal scaling of the API layer** — enabled directly by the stateless backend principle (Section 3.8, 13.3).
- **Parallel parsing** — file-level parsing is embarrassingly parallel and is a near-term performance target (see Section 21).

### 19.3 Known Limits (v1)
Very large multi-module monoliths may push PKG construction time and graph size into ranges that haven't been load-tested yet; this is an explicit area for future benchmarking rather than a solved problem.

---

## 20. Logging & Monitoring

Each subsystem exposes metrics relevant to its own responsibility so problems can be localized quickly:

| Metric | Source |
|---|---|
| Parser duration per file / per project | Parser Engine |
| PKG size (node count, edge count) | PKG Builder |
| Graph query latency | Diagram Engine, AI Gateway |
| AI response time and token usage | AI Gateway |
| Analysis job success/failure rate | Job orchestration layer |
| Security findings count per project | Security Analyzer |

These metrics exist to answer two questions during debugging: which stage of the pipeline failed, and which stage is the current performance bottleneck.

---

## 21. Future Architecture

Planned extension points, all designed to integrate as new **consumers** of the existing PKG rather than changes to the parsing pipeline:

- **Gradle support** — new Import/Discovery adapter; PKG model unchanged
- **Kotlin support** — new Parser adapter alongside the existing JavaParser-based one
- **Multi-module project support** — extending the `Module` node type already present in the schema (Section 5.2)
- **Incremental re-analysis** — rebuilding only the affected subgraph after a source change, instead of a full rebuild
- **Parallel parsing** — file-level parse jobs distributed across worker threads
- **IDE plugin integration** — exposing PKG queries over a lighter protocol for in-editor use
- **Plugin ecosystem** — Metrics Engine, Documentation Engine, and custom analyzers as first-class PKG consumers

---

## 22. Trade-offs Summary

| Decision | Benefit | Cost |
|---|---|---|
| PKG-based architecture | Single source of truth, consistent outputs across every feature | Upfront graph-construction cost per analysis job |
| Deterministic-first analysis | Reliable, reproducible results | No visibility into runtime/dynamic behavior |
| Static analysis only, never execute uploaded code | Fast and safe by construction | Can't observe behavior that only appears at runtime |
| AI applied after analysis, on scoped subgraphs | Low token cost, low hallucination risk, provider-outage resilience | Explanation quality is bounded by PKG completeness |
| Strict layering and loose coupling | Each engine independently testable and replaceable | More components and interfaces to coordinate |
| Immutable PKG, rebuild rather than patch | Guaranteed consistency for every consumer | Any source change requires a full re-analysis in v1 |
| Stateless backend | Simple horizontal scaling | Job state must be explicitly persisted rather than kept in memory |

---

## Closing Note

The single decision that shapes everything else in this document is treating the Project Knowledge Graph as a product, not an implementation detail. Every capability in SpringLens exists because the graph exists. Diagrams are graph queries, playback is a graph traversal, AI answers are graph subgraphs turned into prompts, and security findings are graph annotations. A well-designed PKG turns "add a new feature" into "add a new consumer of existing knowledge" instead of "write a new analysis pipeline." That property is what gives this architecture room to grow without a rewrite.
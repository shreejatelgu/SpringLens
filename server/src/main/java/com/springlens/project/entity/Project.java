package com.springlens.project.entity;

import com.springlens.project.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/*
 * @Entity
 * Marks this class as a JPA entity.
 *
 * Every object of this class represents one row
 * inside the "projects" table.
 */
@Entity

/*
 * Explicitly names the table.
 * This prevents issues if we ever rename the class.
 */
@Table(name = "projects")

/*
 * Lombok Annotations
 *
 * @Getter -> Generates getters for every field.
 * @Setter -> Generates setters for every field.
 * @Builder -> Allows creating objects using Builder Pattern.
 * @NoArgsConstructor -> Required by Hibernate.
 * @AllArgsConstructor -> Useful when creating objects manually.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    /*
     * Every uploaded project needs a unique identifier.
     *
     * UUID is preferred over Long because:
     *
     * - impossible to guess IDs
     * - safe for distributed systems
     * - globally unique
     * - frontend can safely reference projects
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*
     * Original uploaded ZIP filename.
     *
     * Example:
     * spring-petclinic.zip
     */
    @Column(nullable = false)
    private String name;

    /*
     * Physical storage location.
     *
     * Example:
     *
     * workspace/
     *      d34f8c...
     *          project.zip
     *
     * Why nullable?
     *
     * Because during entity creation we don't yet know
     * where the ZIP will finally be stored.
     *
     * We update this after StorageService finishes.
     */
    private String uploadPath;

    /*
     * Build tool detected AFTER analysis.
     *
     * Examples:
     *
     * Maven
     * Gradle
     *
     * At upload time we don't know this yet,
     * so it should remain null.
     */
    private String buildTool;

    /*
     * Programming language.
     *
     * Examples:
     *
     * Java
     * Kotlin
     * Groovy
     *
     * Again, detected after parsing.
     */
    private String language;

    /*
     * Current lifecycle stage of the project.
     *
     * We use Enum instead of String because:
     *
     * ✔ Compiler prevents typos.
     * ✔ Only valid values are allowed.
     * ✔ Database stores readable text.
     *
     * Database Example:
     *
     * UPLOADED
     * ANALYZING
     * COMPLETED
     * FAILED
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    /*
     * Time when project was uploaded.
     *
     * Useful for:
     *
     * - Dashboard
     * - Sorting
     * - Analytics
     * - Audit logs
     */
    @Column(nullable =false)
    private LocalDateTime uploadedAt;

}
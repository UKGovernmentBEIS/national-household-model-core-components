package uk.org.cse.stockimport.repository;

import java.util.List;

import com.google.common.base.Optional;

import uk.org.cse.stockimport.domain.IBasicDTO;

/**
 * An interface encapsulating a collection of {@link IBasicDTO} implementations,
 * which all associate to the same AA Code.
 *
 * @author hinton
 * @since 1.0
 */
public interface IHouseCaseSources<Q> {

    /**
     * @return the aacode which all the DTOs in this provider have
     * @since 1.0
     */
    public String getAacode();

    /**
     * Convenience method for getting a single value; this is equivalent to      <code>
     * 	getAll(clazz).get(0)
     * </code> with associated null value / multiple value checks.
     *
     * @param clazz
     * @return a single {@link IBasicDTO} implementation which implements the
     * given class.
     * @throws IllegalArgumentException if there is more than one DTO for the
     * given class.
     * @since 1.0
     */
    public <T extends Q> Optional<T> getOne(final Class<T> clazz) throws IllegalArgumentException;

    /**
     * Convenience method for getting a single value; this is equivalent to      <code>
     * 	getAll(clazz).get(0)
     * </code> with associated null value / multiple value checks.
     *
     * @param clazz
     * @return a single {@link IBasicDTO} implementation which implements the
     * given class.
     * @throws IllegalArgumentException if there is more than one DTO for the
     * given class.
     * @throws NoSuchDTOException
     * @since 1.0
     */
    public <T extends Q> T requireOne(final Class<T> clazz) throws IllegalArgumentException;

    /**
     * @param clazz
     * @return a list of all the {@link IBasicDTO} implementations which
     * implement the given class.
     * @since 1.0
     */
    public <T extends Q> List<T> getAll(final Class<T> clazz);

    /**
     * The survey year when these things happened.
     *
     * @return
     * @since 1.0
     */
    public int getSurveyYear();
}

package uk.org.cse.nhm.simulator.state.dimensions.time;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.let.ILets;

/**
 * To support different degrees of predictability, the time dimension contains a time object
 * which may have different dates under different keys. Particular functions must then request
 * the right key, so that they can either feel like they are in the future (if they are predictable)
 * or the present (if they are unpredictable).
 * 
 * If this seems weird to you, that probably means you are a normal, healthy invidivual.
 */
public interface ITime {
	/**
	 * This object is used to store the current type of time within the {@link ILets}.
	 * For example, when the carbon factors are being computed, if we need to run another function
	 * we will introduce the right enum key into the lets under TIME_KEY.
	 */
	public static final Object TIME_KEY = new Object();
	public DateTime get(final XForesightLevel key);
	public DateTime get(final ILets lets);
	public DateTime get(Optional<XForesightLevel> foresight, ILets lets);
	public Set<XForesightLevel> predictableLevels();
}

package uk.org.cse.nhm.simulator.obligations;

import java.util.List;

import com.google.common.base.Optional;

import uk.org.cse.commons.collections.branchinglist.IBranchingList;
import uk.org.cse.nhm.hom.ICopyable;

/**
 * A dwelling's obligation history records all of the obligations into which a
 * dwelling has entered; this includes dead obligations, which will not produce
 * any future transactions
 *
 * @author hinton
 * @since 3.7.0
 *
 */
public interface IObligationHistory extends IBranchingList<IObligation>, ICopyable<IObligationHistory> {

    <T extends IObligation> Optional<T> getObligation(Class<T> type);

    <T extends IObligation> List<T> getObligations(Class<T> type);
}

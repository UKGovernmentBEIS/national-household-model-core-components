package uk.org.cse.nhm.simulation.scenario;

/**
 * ScenarioBuildException.
 *
 * @author richardt
 * @version $Id: ScenarioBuildException.java 94 2010-09-30 15:39:21Z richardt
 * @since 1.2.0-STMICHAELS
 */
public class ScenarioBuildException extends Exception {

    private static final long serialVersionUID = 1L;

    public ScenarioBuildException(String msg) {
        super(msg);
    }

    /**
     * Default Constructor.
     */
    public ScenarioBuildException(Exception e) {
        super(e);
    }
}

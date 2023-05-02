package de.qaware.cloudcomputing.parser;

import com.github.amsacode.predict4java.*;
import de.qaware.cloudcomputing.tle.TleMember;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Date;
import java.time.LocalDate;

@ApplicationScoped
public class SatPosCalculator {

    @Inject
    TleParser tleParser;

    public SatPassTime getNextPass(TleMember tleRecord) throws SatNotFoundException {
        PassPredictor passPredictor = getPassPredictor(tleRecord);
        return passPredictor.nextSatPass(getNow());
    }

    public SatPos getSatPos(TleMember tleRecord) throws SatNotFoundException {
        PassPredictor passPredictor = getPassPredictor(tleRecord);
        return passPredictor.getSatPos(getNow());
    }

    private PassPredictor getPassPredictor(TleMember tleRecord) throws SatNotFoundException {
        TLE tle = tleParser.parseTLE(tleRecord);
        GroundStationPosition groundStationPosition = new GroundStationPosition(47, 12, 400, "Rosenheim");
        return new PassPredictor(tle, groundStationPosition);
    }

    private static Date getNow() {
        return Date.valueOf(LocalDate.now());
    }

}

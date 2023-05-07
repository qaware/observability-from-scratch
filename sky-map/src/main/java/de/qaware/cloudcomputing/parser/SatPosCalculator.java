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

    public SatPos getSatPos(TleMember tleRecord) throws SatNotFoundException {
        TLE tle = tleParser.parseTLE(tleRecord);
        GroundStationPosition groundStationPosition = new GroundStationPosition(47, 12, 400, "Rosenheim");
        PassPredictor passPredictor = new PassPredictor(tle, groundStationPosition);
        SatPos satPos = passPredictor.getSatPos(Date.valueOf(LocalDate.now()));
        return satPos;
    }

}

package de.qaware.cloudcomputing.satellite;

import com.github.amsacode.predict4java.*;
import de.qaware.cloudcomputing.parser.SatPosCalculator;
import de.qaware.cloudcomputing.parser.TleParser;
import de.qaware.cloudcomputing.tle.TleClient;
import de.qaware.cloudcomputing.tle.TleMember;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.smallrye.mutiny.Uni;
import lombok.Data;
import lombok.extern.jbosslog.JBossLog;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
import org.apache.commons.math3.geometry.euclidean.threed.SphericalCoordinates;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import java.sql.Date;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Map;

import static org.apache.commons.math3.geometry.euclidean.threed.Vector3D.PLUS_J;

@JBossLog
@Consumes(MediaType.APPLICATION_JSON)
@Path("/satellite")
@ApplicationScoped
public class SatelliteResource {

    @Inject
    @RestClient
    TleClient tleClient;

    @Inject
    SatPosCalculator satPosCalculator;

    @GET
    @WithSpan
    @Path("/{satelliteId}")
    public RestResponse<SatPos> getPrediction(@PathParam("satelliteId") @SpanAttribute int satelliteId) {
        try {
            TleMember tleRecord = tleClient.getRecord(satelliteId);
            logResponse(tleRecord);
            SatPos satPos = satPosCalculator.getSatPos(tleRecord);
            log(satPos);
            return RestResponse.ok(satPos);
        } catch (Exception e) {
            log.error(e);
            return RestResponse.serverError();
        }
    }

    private void logResponse(TleMember tleMember) {
        log.info(tleMember);

    }

    private void log(SatPos satPos) {
        log.info(satPos);
    }

}

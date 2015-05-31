package com.example.REAPRO.myapplication.backend.dominio;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "aulaApi",
        version = "v1",
        resource = "aula",
        namespace = @ApiNamespace(
                ownerDomain = "dominio.backend.myapplication.REAPRO.example.com",
                ownerName = "dominio.backend.myapplication.REAPRO.example.com",
                packagePath = ""
        )
)
public class AulaEndpoint {

    private static final Logger logger = Logger.getLogger(AulaEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Aula.class);
    }

    /**
     * Returns the {@link Aula} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Aula} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "aula/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Aula get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Aula with ID: " + id);
        Aula aula = ofy().load().type(Aula.class).id(id).now();
        if (aula == null) {
            throw new NotFoundException("Could not find Aula with ID: " + id);
        }
        return aula;
    }

    /**
     * Inserts a new {@code Aula}.
     */
    @ApiMethod(
            name = "insert",
            path = "aula",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Aula insert(Aula aula) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that aula.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(aula).now();
        logger.info("Created Aula with ID: " + aula.getId());

        return ofy().load().entity(aula).now();
    }

    /**
     * Updates an existing {@code Aula}.
     *
     * @param id   the ID of the entity to be updated
     * @param aula the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Aula}
     */
    @ApiMethod(
            name = "update",
            path = "aula/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Aula update(@Named("id") Long id, Aula aula) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(aula).now();
        logger.info("Updated Aula: " + aula);
        return ofy().load().entity(aula).now();
    }

    /**
     * Deletes the specified {@code Aula}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Aula}
     */
    @ApiMethod(
            name = "remove",
            path = "aula/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Aula.class).id(id).now();
        logger.info("Deleted Aula with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "aula",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Aula> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Aula> query = ofy().load().type(Aula.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Aula> queryIterator = query.iterator();
        List<Aula> aulaList = new ArrayList<Aula>(limit);
        while (queryIterator.hasNext()) {
            aulaList.add(queryIterator.next());
        }
        return CollectionResponse.<Aula>builder().setItems(aulaList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Aula.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Aula with ID: " + id);
        }
    }
}
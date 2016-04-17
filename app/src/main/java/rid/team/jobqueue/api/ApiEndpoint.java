package rid.team.jobqueue.api;

import retrofit.Endpoint;
import rid.team.jobqueue.Constants;

/**
 * Created by bendik on 17.03.16.
 */
public class ApiEndpoint implements Endpoint {
    @Override
    public String getUrl() {
        return Constants.PRODUCTION_API_ENDPOINT_URL;
    }

    @Override
    public String getName() {
        return Constants.PRODUCTION_API_ENDPOINT_NAME;
    }
}

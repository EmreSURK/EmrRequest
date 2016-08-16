public class ServiceCall {
    public static final String BASEURL = ""

    public void SessionedPostRequest(final Context c, String method, JsonObject body, final GeneralInterface generalInterface) {
        if (body == null)
            body = new JsonObject();

        body.addProperty("token", getUserTokenFromPref());
        Ion.with(c)
            .load(ServiceCall.BASEURL + method)
            .setJsonObjectBody(body)
            .asJsonObject()
            .withResponse()
            .setCallback(
                new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {
                        try {
                            if (result.getResult().get("err") != JsonNull.INSTANCE) {
                                Toast.makeText(c, result.getResult().get("errMsg").getAsString(), Toast.LENGTH_SHORT).show();
                            } else {
                                if (generalInterface != null) {
                                    try {
                                        generalInterface.getResult(result.getResult().get("data").getAsJsonObject());
                                    } catch (Exception ex) {
                                        generalInterface.getResult(result.getResult().get("data").getAsJsonArray());
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            // showing errors may cause errors due to Activity Lifecycle.
                            try {
                                Toast.makeText(c, "Sunucu ile ilgili hata oluştu.", Toast.LENGTH_SHORT).show();
                            } catch (Exception exi) {

                            }
                        }
                    }
                }
            );
    }

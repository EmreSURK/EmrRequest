new ServiceCall().SessionedPostRequest(getActivity(), "methodName", null, new GeneralInterface() {
                    @Override
                    public void getResult(Object object) {
                        List<ItemClass> = new ItemClass().ParseQuestionJson((JsonArray) object)
                    }
                });

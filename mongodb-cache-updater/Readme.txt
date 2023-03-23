1) RestApi running on Mocakaroo
2) Start Docker Compose
    TODO: persist to mongdb todo
3) To Test: ( Validate log files for Cache to REst)
      curl -X GET http://localhost:8080/test-user
      curl -X GET  http://localhost:8080/test-user/44
      curl -X GET http://localhost:8080/test-user/with-cache/44
      curl -X GET http://localhost:8080/test-user/with-caffeine-cache/44

# idee
An app that gives ideas about trips for the next weekend.

How to use:
`curl -X POST --data "userLon=10.729174" --data "userLat=59.916649" --data "searchRadius=20" http://localhost:8080/get-ideas`

TODOs:
- write unit tests
- return JSON error responses
- enforce maven checkstyle
- add mutation tests
- add cache
- add Trace ID to requests
- check duplicated trips
- update readme

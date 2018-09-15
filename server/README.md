# idee
An app that gives ideas about trips for the next weekend.

How to use:
`curl -X POST --data "userLon=10.729174" --data "userLat=59.916649" --data "searchRadius=20" http://localhost:8080/get-ideas`

TODOs:
- refactor code with proper layering
- write unit tests
- return JSON error responses
- enforce maven checkstyle
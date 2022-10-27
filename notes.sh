# =========
# ==NOTES==
# =========


# CURL
# ----

# GET request
curl -X GET 'http://localhost:8081/calculate?expression=1*6'

# POST request
curl -X POST -H 'content-type: application/json' 'http://localhost:8081/calculate' -d '{"left":"1","right":"6","op":"*"}'

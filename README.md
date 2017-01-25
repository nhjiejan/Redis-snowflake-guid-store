# GUID Generator 

This guid generator uses Redis as a historic backend Key:Value store. The client sends an array of fields which will
form a sha1 Hash. If Redis has seen this hash as a key before it will return its associated unqie key (guid). Unique key generation is created with a snowflake server.
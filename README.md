# Contacts

# Create a contact

curl -X POST -H "Content-Type: application/json" -d '{"firstName": "Arsen", "lastName": "
Yeritsyan"}' http://localhost:8080/contacts

# Get a contact by ID

curl http://localhost:8080/contacts/1

# Get a list of contacts

curl http://localhost:8080/contacts?page=0&size=10

# Search contacts by firstName and lastName

curl http://localhost:8080/contacts?firstName=Arsen&lastName=Yeritsyan

# Update a contact

curl -X PUT -H "Content-Type: application/json" -d '{"firstName": "Arsen", "lastName": "
Yeritsyan"}' http://localhost:8080/contacts/1

# Delete a contact

curl -X DELETE http://localhost:8080/contacts/1

# Add a phone number to a contact

curl -X POST -H "Content-Type: application/json" -d '{"phoneNumber": "1234567890", "label": "
Home"}' http://localhost:8080/contacts/1/phoneNumbers

# Update a phone number

curl -X PUT -H "Content-Type: application/json" -d '{"phoneNumber": "9876543210", "label": "
Work"}' http://localhost:8080/contacts/1/phoneNumbers/1

# Delete a phone number

curl -X DELETE http://localhost:8080/contacts/1/phoneNumbers/1

# Add an email to a contact

curl -X POST -H "Content-Type: application/json" -d '{"email": "arsen@example.com", "domainName": "
example.com"}' http://localhost:8080/contacts/1/emails

# Update an email

curl -X PUT -H "Content-Type: application/json" -d '{"email": "updated@example.com", "domainName": "
example.com"}' http://localhost:8080/contacts/1/emails/1

# Delete an email

curl -X DELETE http://localhost:8080/contacts/1/emails/1

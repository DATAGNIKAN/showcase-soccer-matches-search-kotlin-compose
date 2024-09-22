#!/bin/sh

/opt/typesense-server --data-dir "$TYPESENSE_DATA_DIR" --api-key "$TYPESENSE_API_KEY" --enable-cors &

until curl -s http://localhost:8108/health > /dev/null; do
  echo "Waiting for Typesense to be available..."
  sleep 5
done

echo "\n\nCreating the collection..."

curl "http://localhost:8108/collections" \
       -X POST \
       -H "Content-Type: application/json" \
       -H "X-TYPESENSE-API-KEY: ${TYPESENSE_API_KEY}" \
       -d '{
         "name": "'"${COLLECTION_NAME}"'",
         "fields": [
           { "name": "date", "type": "string", "facet": true },
           { "name": "home", "type": "string", "facet": true },
           { "name": "away", "type": "string", "facet": true },
           { "name": "home_goals", "type": "int32", "facet": true },
           { "name": "away_goals", "type": "int32", "facet": true },
           { "name": "home_total_points", "type": "float", "facet": true },
           { "name": "away_total_points", "type": "float", "facet": true }
         ]
       }'

echo "\n\nImporting the data..."

curl -H "X-TYPESENSE-API-KEY: ${TYPESENSE_API_KEY}" \
      -X POST \
      -T ./default_data.jsonl \
      "http://localhost:${TYPESENSE_PORT}/collections/${COLLECTION_NAME}/documents/import?action=create"

echo "\n\nEND..."

wait

FROM typesense/typesense:28.0.rc7

WORKDIR /typesense

ENV TYPESENSE_DATA_DIR=/data
ENV TYPESENSE_API_KEY=xyz
ENV TYPESENSE_PORT=8108

ENV COLLECTION_NAME=soccer_matches

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

EXPOSE ${TYPESENSE_PORT}

COPY default_data.jsonl .
COPY entrypoint.sh .

RUN chmod +x entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]
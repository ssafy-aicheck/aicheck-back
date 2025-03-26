db = db.getSiblingDB("reportdb");
db.createUser({
    user: "appuser",
    pwd: "securepass",
    roles: [{ role: "readWrite", db: "reportdb" }]
});
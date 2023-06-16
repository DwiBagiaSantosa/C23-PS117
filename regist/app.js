const express = require("express");
const bodyParser = require("body-parser");

const router = require("./routes/router");
const sequelize = require("./config/db");

const swaggerjsdoc = require("swagger-jsdoc");
const swaggerui = require("swagger-ui-express");

const app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const port = process.env.PORT || 8081;

const options = {
  definition: {
    openapi: "3.0.0",
    info: {
      title: "Calorie Sense API Documentation with Swagger",
      version: "0.1.0",
      description:
        "This API application made with Express and documented with Swagger",
    },
    servers: [
      {
        url: "http://localhost:8081/",
      },
    ],
  },
  apis: ["../regist/routes/*.js", "../users/routes/*.js"],
};

const spacs = swaggerjsdoc(options);
app.use("/api-docs", swaggerui.serve, swaggerui.setup(spacs));

app.get("/", (req, res) => {
  res.send("Server is running!");
});
app.use("/", router);

const startServer = async () => {
  try {
    await sequelize.authenticate();
    console.log("Connection has been established successfully.");

    app.listen(port, () => {
      console.log(`server running on port: ${port}`);
    });
  } catch (error) {
    console.error("Unable to connect to the database:", error);
  }
};

startServer();

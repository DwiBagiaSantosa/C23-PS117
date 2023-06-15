const express = require("express");
const cookieParser = require("cookie-parser");
require("dotenv").config();
const swaggerjsdoc = require("swagger-jsdoc");
const swaggerui = require("swagger-ui-express");

const authRouter = require("./router/authRoutes");
const { verifyToken } = require("./middleware/authMiddleware");

const app = express();
const port = process.env.PORT || 5000;

// middleware
app.use(express.json());
app.use(cookieParser());

// routes
app.get("/", (req, res) => {
  res.send("This root path");
});
app.use("/", authRouter);
app.get("/users", verifyToken, (req, res) => {
  res.send("This is Dashboard User");
});

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
        url: "http://localhost:5000/",
      },
    ],
  },
  apis: ["./router/*.js", "../regist/routes/*.js", "../users/routes/*.js"],
};

const spacs = swaggerjsdoc(options);
app.use("/api-docs", swaggerui.serve, swaggerui.setup(spacs));

// connection
app.listen(port, () => {
  console.log(`server running on port: ${port}`);
});

const express = require("express");
const cookieParser = require("cookie-parser");
require("dotenv").config();

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

// connection
app.listen(port, () => {
  console.log(`server running on port: ${port}`);
});

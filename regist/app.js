const express = require("express");
const bodyParser = require("body-parser");

const router = require('./routes/router')
const sequelize = require('./config/db')

const app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const port = process.env.PORT || 8081;

app.get("/", (req, res) => {res.send("Server is running!")});
app.use('/', router);

const startServer = async () => {
    try {
        await sequelize.authenticate();
        console.log('Connection has been established successfully.');

        app.listen(port, () => {
            console.log(`server running on port: ${port}`);
        })
      } catch (error) {
        console.error('Unable to connect to the database:', error);
      }
}

startServer();

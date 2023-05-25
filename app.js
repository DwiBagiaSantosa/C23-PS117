const express = require('express');
const mongoose = require('mongoose');
const authRouter = require('./router/authRoutes');
const cookieParser = require('cookie-parser');

const app = express();
const port = 5000;

// middleware
app.use(express.json());
app.use(cookieParser());

// routes
app.get('/', (req, res) => {res.send('This is first page')});
app.use(authRouter);

// connection
const dbConnection = '';
mongoose.connect(dbConnection, { useNewUrlParser: true, useUnifiedTopology: true, useCreateIndex:true })
  .then((result) => app.listen(port))
  .catch((err) => console.log(err));

const express = require('express');
const userRouter = require('./routes/router');

const app = express();
const port = process.env.PORT || 8082;

// middleware
app.use(express.json());

// routes
app.get('/', (req, res) => {res.send('Hello...')});
app.use('/', userRouter);

// db connection
app.listen(port, () => {
    console.log(`Server is running on port: ${port}`);
  });

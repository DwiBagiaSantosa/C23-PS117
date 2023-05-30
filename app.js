const express = require('express');
const authRouter = require('./router/authRoutes');
const cookieParser = require('cookie-parser');
const { verifyToken } = require('./middleware/authMiddleware');
require('dotenv').config();

const app = express();
const port = process.env.PORT || 5000;

// middleware
app.use(express.json());
app.use(cookieParser());

// routes
app.get('/dashboard', verifyToken, (req, res) => {res.send('This is Dashboard')});
app.get('/', (req, res) => {res.send('This is Homepage')});
app.use(authRouter);

// connection
app.listen(port, () => {
  console.log(`server running on port: ${port}`);
});

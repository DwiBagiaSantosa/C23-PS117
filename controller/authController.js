const User = require('../models/dbmodel');
const { errorHandler } = require('./authHandler');

// variables
// tokenAge = 7 * 24 * 60 * 60;

// PATH handler
const signupPage = (req, res) => {
  res.send('signup page');
}

const loginPage = (req, res) => {
  res.send('login page');
}

const createNewUser = async (req, res) => {
  const { email, password } = req.body;

  try {
    const user = await User.create({ email, password });
    // const token = createToken(user._id);
    // res.cookies('token', token, { httpOnly: true, maxAge: tokenAge * 1000 })
    res.status(201).json(user).send(console.log('new user creted'));
  }
  catch(err) {
    const error = errorHandler(err);
    res.status(400).json({ error });
  }
}

const userLogin = async (req, res) => {
  const { email, password } = req.body;

  try {
    const user = await User.login(email, password);
    // const token = createToken(user._id);
    // res.cookies('token', token, { httpOnly: true, maxAge: tokenAge * 1000 })
    res.status(200).json({ user: user._id }).send(console.log('user founded'));
  } catch(err) {
    const error = errorHandler(err);
    res.status(400).json({ error });
  }
}

const userLogout = (req, res) => {
  res.send('user logout');
}

module.exports = {
  signupPage,
  loginPage,
  createNewUser,
  userLogin,
  userLogout
}
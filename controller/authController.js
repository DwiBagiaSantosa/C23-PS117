const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const nodemailer = require('nodemailer');
const Mailgen = require('mailgen');
const { isEmail } = require('validator');
const User = require('../models/users');

// variable
const tokenAge = 3 * 24 * 60 * 60;

// create token handler
const createToken = ({ email, hashPassword }) => {
  return jwt.sign({ email, hashPassword }, process.env.ACCESS_TOKEN_SECRET, {
    expiresIn: tokenAge
  });
}

// PATH Handler
const signupPage = async (req, res) => {
  res.send('signup page');
}

const loginPage = (req, res) => {
  res.send('login page');
}

const passwordPage = (req, res) => {
  res.send('reset Password Page');
}

const createNewUser = async (req, res) => {
  const { name, email, password, confPassword, gender, age, tall, weight } = req.body;

  if (!isEmail(email)) {
    return res.status(400).json({
      message: 'Please enter valid email'
    });
  }
  if (password !== confPassword) {
    return res.status(400).json({
      message: 'Password and Confirm Password didnt match'
    });
  }

  const salt = await bcrypt.genSalt();
  const hashPassword = await bcrypt.hash(password, salt);
  
  try {
    const user = await User.createNewUser({ name, email, hashPassword, gender, age, tall, weight });
    const token = createToken({ email, hashPassword });
    res.cookie('access_token', token, { httpOnly: true, maxAge: tokenAge * 1000 });
    res.status(201).json({
      message: 'create new user success',
      data: {
        email,
        hashPassword
      }
    }).send(console.log('new user creted'));
  }
  catch(error) {
    res.status(500).json({
      message: 'server error',
      serverMessage: error.message
    }).send(console.log('create user failed'));
  }
}

const userLogin = async (req, res) => {
  const { email, password } = req.body;

  try {
    const [user] = await User.getUser({ email });
    const userId = user[0].id;
    const userEmail = user[0].email;
    const userPassword = user[0].password;

    const passMatch = await bcrypt.compare(password, userPassword);
    
    if(!passMatch) {
      return res.status(400).json({
        message: 'incorrect password'
      });
    }

    const token = createToken({ userEmail, userPassword });
    res.cookie('access_token', token, { httpOnly: true, maxAge: tokenAge * 1000 });

    res.status(200).json({
      message: 'user login success',
      data: {
        id: userId,
        email: userEmail,
      }
    });
  } catch (error) {
    res.status(500).json({
      message: 'internal server error',
      serverMessage: error.message
    });
  }
}

const resetPassword = async (req, res) => {
  const { email } = req.body;
  
  try {
    const [user] = await User.getUser({ email });
    const userEmail = user[0].email;
    const userPassword = user[0].password;

    if(!user) {
      return res.status(400).json({
        error: 'User with this email does not exist'
      });
    }

    const token = createToken({ userEmail, userPassword });

    // create email config
    let config = {
      service : 'gmail',
      auth : {
          user: EMAIL,
          pass: password
      }
    }

    let transporter = nodemailer.createTransport(config);

    // build email template
    let mailGenerator = new Mailgen({ 
      theme: 'default',
      product : {
        name: "Mailgen",
        link : 'https://mailgen.js/'
      }
     });

     let response = {
      body: {
          name : "DailyintakeCalories",
          intro: "Reset your password!",
          table : {
              data : [
                  {
                      Link : "This is Link"
                  }
              ]
          },
          outro: "please click the link to reset your password"
      }
    }

    let mail = mailGenerator.generate(response);

    let message = {
      from : process.env.EMAIL,
      to : userEmail,
      subject: " Reset Password | DailyintakeCalories",
      html: mail
    }

    transporter.sendMail(message).then(() => {
      return res.status(201).json({
          message: "Email has been sent"
      });
    }).catch(error => {
      return res.status(500).json({
        message: error
      });
    });

  } catch (error) {
    res.status(500).json({
      message: 'internal server error',
      serverMessage: error.message
    });
  }
}

const userLogout = (req, res) => {
  res.cookie('access_token', '', { maxAge: 1 });
  res.redirect('/');
}

module.exports = {
  signupPage,
  loginPage,
  passwordPage,
  createNewUser,
  userLogin,
  resetPassword,
  userLogout
}
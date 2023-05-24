// const token = require('jsonwebtoken');

// variables
// tokenAge = 7 * 24 * 60 * 60;

// error handler
const errorHandler = (err) => {
  console.log(err.message, err.code);
  let error = { email:'', password:'' }
  
  //duplicate email
  if(err.code === 11000) {
    error.email = 'email is already registered'
    return error;
  }

  //validation email and password
  if(err.message.includes('users validation failed')) {
    Object.values(err.errors).forEach(({ properties }) => {
      error[properties.path] = properties.message;
    });
  }

  // For login
  // incorrect email
  if (err.message === 'inccorrect email!') {
    error.email = 'Email is not registered';
  }

  // incorrect password
  if (err.message === 'incorrect password!') {
    error.password = 'Password is not correct';
  }
  
  return error;
}

// create token handler
// const createToken = (id) => {
//   return token.sign({ id }, 'dailyintakecalorie', {
//     expiresIn: tokenAge
//   });
// }

module.exports = { errorHandler }
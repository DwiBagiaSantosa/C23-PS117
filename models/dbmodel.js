const mongoose = require('mongoose');
const { isEmail } = require('validator');
const bcrypt = require('bcrypt');

const { Schema } = mongoose;

//schema db
const schema = new Schema({
  email: {
    type: String,
    required: [true, 'please enter email'],
    unique: true,
    lowercase: true,
    validate: [isEmail, 'please enter valid email'],
  },
  password: {
    type: String,
    required: [true, 'please enter password'],
    minlength: [8, 'minimum password length is 8 character'],
  }
});

// hash password before store to db
schema.pre('save', async function(next) {
  const salt = await bcrypt.genSalt();
  this.password = await bcrypt.hash(this.password, salt);
  next();
});

// user login validation method
schema.statics.login = async function(email, password) {
  const user = await this.findOne({ email });
  if (user) {
    const pswd = await bcrypt.compare(password, user.password);
    if (pswd) {
      return user;
    }
    throw Error('incorrect password!');
  }
  throw Error('inccorrect email!');
}

const user = mongoose.model('users', schema);

module.exports = user;
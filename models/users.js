const pool = require('../config/db').pool;


const getUsers = () => {
  const query = 'SELECT * FROM users';

  return pool.execute(query);
}

const createNewUser = ({ name, email, hashPassword, gender, age, tall, weight }) => {
  const query = ` INSERT INTO users (name, email, password, gender, age, tall, weight) 
                  VALUES('${name}', '${email}', '${hashPassword}', ${gender}, ${age}, ${tall}, ${weight})`;

  return pool.execute(query);
}

const getUser = ({ email }) => {
  const query = `SELECT * FROM users WHERE email='${email}'`;
  
  return pool.execute(query);
}

module.exports = {
  getUsers, 
  createNewUser,
  getUser
};
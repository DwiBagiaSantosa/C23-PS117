const Sequelize = require("sequelize");

const sequelize = new Sequelize('users', 'root', 'dbpassword', {
    host: '34.101.219.120',
    dialect: 'mysql',
  });

module.exports = sequelize;
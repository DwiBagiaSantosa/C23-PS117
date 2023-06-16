const { DataTypes } = require('sequelize');
const sequelize = require('../config/db');

const User = sequelize.define('users', {
  id: {
    type: DataTypes.INTEGER,
    allowNull: false,
    primaryKey: true,
    autoIncrement: true,
  },
  name: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  email: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true,
  },
  password: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  gender: {
    type: DataTypes.ENUM('L','P'),
    allowNull: false,
  },
  age: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  height: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  weight: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  basictarget: {
    type: DataTypes.INTEGER,
  },
  bmr: {
    type: DataTypes.INTEGER,
    allowNull: false,
  },
  calories: {
    type: DataTypes.INTEGER,
  },
  token: {
    type: DataTypes.STRING,
  },
}, {
    timestamps: false // Disable the default createdAt and updatedAt columns
});

module.exports = User;
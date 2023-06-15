const jwt = require("jsonwebtoken");
const crypto = require("crypto");
const bcrypt = require('bcrypt');

const jwtSecret = crypto.randomBytes(32).toString("hex");
const { Router } = require('express');

const User = require('../models/users');
const router = Router();

router.get("/users", async (req, res) => {
  try {
    const users = await User.findAll();
    res.status(200).json({ users });
  } catch (error) {
    res.status(500).json({ error: true, message: error });
  }
});

router.post("/register", async (req, res) => {
  const { name, email, password, gender, age, height, weight } = req.body;

  try {
    const existingUser = await User.findOne({
        where: { email: email },
      });
    if (existingUser) {
      return res
        .status(400)
        .json({ error: true, message: "Email already exists" });
    }

    if (password.length < 8) {
      return res.status(400).json({
        error: true,
        message: "Password must be at least 8 characters long",
      });
    }

    const bmr = calculateBMR(gender, age, height, weight);

    const newUser = User.build({
      name,
      email,
      password,
      gender,
      age,
      height,
      weight,
      bmr,
    });

    await newUser.save();

    res.json({ error: false, message: "User Created" });
  } catch (error) {
    res.status(500).json({ error: true, message: error });
  }
});

function calculateBMR(gender, age, height, weight) {
  let bmr = 0;
  if (gender === "L") {
    bmr = 10 * weight + 6.25 * height - 5 * age + 5;
  } else if (gender === "P") {
    bmr = 10 * weight + 6.25 * height - 5 * age - 161;
  }
  return bmr;
}

router.post("/login", async (req, res) => {
  const { email, password } = req.body;

  try {
    const user = await User.findOne({
        where: { email: email },
      });

    if (!user) {
      return res.status(400).json({ error: true, message: "User not found" });
    }

    if (user.password !== password) {
      return res
        .status(400)
        .json({ error: true, message: "Incorrect password" });
    }

    const loginResult = {
      userId: user.id,
      name: user.name,
      email: user.email,
      password: user.password,
      gender: user.gender,
      age: user.age,
      height: user.height,
      weight: user.weight,
      bmr: user.bmr,
    //   token: jwt.sign({ userId: user._id }, jwtSecret), 
    };

    res.json({ error: false, message: "success", loginResult });
  } catch (error) {
    res.status(500).json({ error: true, message: error });
  }
});

router.post("/update-bmr", async (req, res) => {
  const { userId, bmr, calories } = req.body;

  try {
    const user = await User.findOne({
      where: { id: userId },
    });

    if (!user) {
      return res.status(400).json({ error: true, message: "User not found" });
    }

    user.bmr = bmr;
    user.calories = calories;
    await user.save();

    res.json({
      error: false,
      message: "BMR and calories updated successfully",
    });
  } catch (error) {
    res.status(500).json({ error: true, message: error });
  }
});

module.exports = router;
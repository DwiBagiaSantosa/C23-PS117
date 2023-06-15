const jwt = require("jsonwebtoken");
const crypto = require("crypto");
const bcrypt = require("bcrypt");

const jwtSecret = crypto.randomBytes(32).toString("hex");
const { Router } = require("express");

const User = require("../models/users");
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
  const { name, email, password, gender, age, tall, weight } = req.body;

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

    const bmr = calculateBMR(gender, age, tall, weight);

    const newUser = User.build({
      name,
      email,
      password,
      gender,
      age,
      tall,
      weight,
      bmr,
    });

    await newUser.save();

    res.json({ error: false, message: "User Created" });
  } catch (error) {
    res.status(500).json({ error: true, message: error });
  }
});

function calculateBMR(gender, age, tall, weight) {
  let bmr = 0;
  if (gender === "L") {
    bmr = 10 * weight + 6.25 * tall - 5 * age + 5;
  } else if (gender === "P") {
    bmr = 10 * weight + 6.25 * tall - 5 * age - 161;
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
      tall: user.tall,
      weight: user.weight,
      bmr: user.bmr,
      //   token: jwt.sign({ userId: user._id }, jwtSecret),
    };

    res.json({ error: false, message: "success", loginResult });
  } catch (error) {
    res.status(500).json({ error: true, message: error });
  }
});

module.exports = router;

/**
 * @swagger
 * tags:
 *   name: Authentication
 *   description: Authentication endpoints
 */

/**
 * @swagger
 * /users:
 *   get:
 *     summary: Get all users
 *     tags: [Authentication]
 *     responses:
 *       200:
 *         description: Returns all users
 *   post:
 *     summary: Create a new user
 *     tags: [Authentication]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             properties:
 *               name:
 *                 type: string
 *               email:
 *                 type: string
 *               password:
 *                 type: string
 *               gender:
 *                 type: string
 *               age:
 *                 type: number
 *               tall:
 *                 type: number
 *               weight:
 *                 type: number
 *             example:
 *               name: John Doe
 *               email: johndoe@example.com
 *               password: password123
 *               gender: male
 *               age: 30
 *               tall: 180
 *               weight: 75
 *     responses:
 *       200:
 *         description: User created successfully
 *       400:
 *         description: Email already exists or invalid password
 *       500:
 *         description: Server error
 */

/**
 * @swagger
 * /login:
 *   post:
 *     summary: User login
 *     tags: [Authentication]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             properties:
 *               email:
 *                 type: string
 *               password:
 *                 type: string
 *             example:
 *               email: johndoe@example.com
 *               password: password123
 *     responses:
 *       200:
 *         description: User logged in successfully
 *       400:
 *         description: User not found or incorrect password
 *       500:
 *         description: Server error
 */

/**
 * @swagger
 * /users:
 *   get:
 *     summary: Get all users
 *     tags: [Authentication]
 *     responses:
 *       200:
 *         description: Returns all users
 */

/**
 * @swagger
 * /register:
 *   post:
 *     summary: Create a new user
 *     tags: [Authentication]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             properties:
 *               name:
 *                 type: string
 *               email:
 *                 type: string
 *               password:
 *                 type: string
 *               gender:
 *                 type: string
 *               age:
 *                 type: number
 *               tall:
 *                 type: number
 *               weight:
 *                 type: number
 *             example:
 *               name: John Doe
 *               email: johndoe@example.com
 *               password: password123
 *               gender: male
 *               age: 30
 *               tall: 180
 *               weight: 75
 *     responses:
 *       200:
 *         description: User created successfully
 *       400:
 *         description: Email already exists or invalid password
 *       500:
 *         description: Server error
 */

/**
 * @swagger
 * /login:
 *   post:
 *     summary: User login
 *     tags: [Authentication]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             properties:
 *               email:
 *                 type: string
 *               password:
 *                 type: string
 *             example:
 *               email: johndoe@example.com
 *               password: password123
 *     responses:
 *       200:
 *         description: User logged in successfully
 *       400:
 *         description: User not found or incorrect password
 *       500:
 *         description: Server error
 */

/**
 * Calculate BMR based on gender, age, tall, and weight
 * @param {string} gender - User's gender ('L' for male, 'P' for female)
 * @param {number} age - User's age
 * @param {number} tall - User
 */

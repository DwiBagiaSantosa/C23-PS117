const jwt = require("jsonwebtoken");
const crypto = require("crypto");

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
    const basictarget = calculateBasicTarget(gender, age, height, weight);

    const newUser = User.build({
      name,
      email,
      password,
      gender,
      age,
      height,
      weight,
      basictarget,
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
  if (gender.toUpperCase() === "L") {
    bmr = 10 * weight + 6.25 * height - 5 * age + 5;
  } else if (gender.toUpperCase() === "P") {
    bmr = 10 * weight + 6.25 * height - 5 * age - 161;
  }
  return bmr;
}

function calculateBasicTarget(gender, age, height, weight) {
  let basictarget = 0;
  if (gender.toUpperCase() === "L") {
    basictarget = 10 * weight + 6.25 * height - 5 * age + 5;
  } else if (gender.toUpperCase() === "P") {
    basictarget = 10 * weight + 6.25 * height - 5 * age - 161;
  }
  return basictarget;
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
      basictarget: user.basictarget,
      bmr: user.bmr,
      token: jwt.sign({ userId: user.id }, jwtSecret),
    };

    res.json({ error: false, message: "success", loginResult });
  } catch (error) {
    res.status(500).json({ error: true, message: error });
  }
});

router.post("/update-bmr", async (req, res) => {
  const { userId, bmr, calories } = req.body;

  try {
    const user = await await User.findOne({
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

/**
 * @swagger
 * tags:
 *   name: Authentication
 *   description: API endpoints for managing user data
 */

/**
 * @swagger
 * /users:
 *   get:
 *     summary: Get all users
 *     tags: [Authentication]
 *     responses:
 *       200:
 *         description: Success
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/UsersResponse'
 *       500:
 *         description: Failed to get users
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */

/**
 * @swagger
 * /register:
 *   post:
 *     summary: Register a new user
 *     tags: [Authentication]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/RegisterRequest'
 *     responses:
 *       200:
 *         description: Success
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/SuccessResponse'
 *       400:
 *         description: Bad Request
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 *       500:
 *         description: Failed to register user
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
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
 *             $ref: '#/components/schemas/LoginRequest'
 *     responses:
 *       200:
 *         description: Success
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/LoginResponse'
 *       400:
 *         description: Bad Request
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 *       500:
 *         description: Failed to login user
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */

/**
 * @swagger
 * /update-bmr:
 *   post:
 *     summary: Update user BMR and calories
 *     tags: [Authentication]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/UpdateBMRRequest'
 *     responses:
 *       200:
 *         description: Success
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/SuccessResponse'
 *       400:
 *         description: Bad Request
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 *       500:
 *         description: Failed to update BMR and calories
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */

/**
 * @swagger
 * components:
 *   schemas:
 *     UsersResponse:
 *       type: object
 *       properties:
 *         users:
 *           type: array
 *           items:
 *             $ref: '#/components/schemas/User'
 *       example:
 *         users:
 *           - name: John Doe
 *             email: john@example.com
 *             gender: M
 *             age: 30
 *           - name: Jane Smith
 *             email: jane@example.com
 *             gender: F
 *             age: 25
 *     User:
 *       type: object
 *       properties:
 *         name:
 *           type: string
 *         email:
 *           type: string
 *         gender:
 *           type: string
 *         age:
 *           type: integer
 *     RegisterRequest:
 *       type: object
 *       properties:
 *         name:
 *           type: string
 *         email:
 *           type: string
 *         password:
 *           type: string
 *         gender:
 *           type: string
 *         age:
 *           type: integer
 *         height:
 *           type: number
 *         weight:
 *           type: number
 *       example:
 *         name: John Doe
 *         email: john@example.com
 *         password: password123
 *         gender: M
 *         age: 30
 *         height: 180
 *         weight: 75
 *     LoginRequest:
 *       type: object
 *       properties:
 *         email:
 *           type: string
 *         password:
 *           type: string
 *       example:
 *         email: john@example.com
 *         password: password123
 *     LoginResponse:
 *       type: object
 *       properties:
 *         error:
 *           type: boolean
 *         message:
 *           type: string
 *         loginResult:
 *           $ref: '#/components/schemas/User'
 *       example:
 *         error: false
 *         message: success
 *         loginResult:
 *           name: John Doe
 *           email: john@example.com
 *           gender: M
 *           age: 30
 *     UpdateBMRRequest:
 *       type: object
 *       properties:
 *         userId:
 *           type: string
 *         bmr:
 *           type: number
 *         calories:
 *           type: number
 *       example:
 *         userId: "123456"
 *         bmr: 1800
 *         calories: 2000
 *     SuccessResponse:
 *       type: object
 *       properties:
 *         error:
 *           type: boolean
 *         message:
 *           type: string
 *       example:
 *         error: false
 *         message: User Created
 *     ErrorResponse:
 *       type: object
 *       properties:
 *         error:
 *           type: boolean
 *         message:
 *           type: string
 *       example:
 *         error: true
 *         message: Internal server error
 */

/**
 * @swagger
 * /users:
 *   get:
 *     summary: Get all users
 *     tags: [Authentication]
 *     responses:
 *       200:
 *         description: Success
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/UsersResponse'
 *       500:
 *         description: Failed to get users
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */

/**
 * @swagger
 * /register:
 *   post:
 *     summary: Register a new user
 *     tags: [Authentication]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/RegisterRequest'
 *     responses:
 *       200:
 *         description: Success
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/SuccessResponse'
 *       400:
 *         description: Bad Request
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 *       500:
 *         description: Failed to register user
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
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
 *             $ref: '#/components/schemas/LoginRequest'
 *     responses:
 *       200:
 *         description: Success
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/LoginResponse'
 *       400:
 *         description: Bad Request
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 *       500:
 *         description: Failed to login user
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */

/**
 * @swagger
 * /update-bmr:
 *   post:
 *     summary: Update user BMR and calories
 *     tags: [Authentication]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/UpdateBMRRequest'
 *     responses:
 *       200:
 *         description: Success
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/SuccessResponse'
 *       400:
 *         description: Bad Request
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 *       500:
 *         description: Failed to update BMR and calories
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/ErrorResponse'
 */

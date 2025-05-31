package Logic;

import Object.DifficultyLatihan;
import Object.DurationLatihan;
import Object.RepetitionLatihan;

public class InitializeLatihan {
    public void inisialisasiLatihan() {
        DifficultyLatihan beginner = new DifficultyLatihan("Beginner");
        DifficultyLatihan intermediate = new DifficultyLatihan("Intermediate");
        DifficultyLatihan advanced = new DifficultyLatihan("Advanced");

        // Beginner
        beginner.addLatihan(new DurationLatihan(
            "Arm Circles",
            "Stand straight, extend arms, make small circles",
            "Shoulder Mobility, WarmUp",
            "WarmUp",
            1,
            30
        ));
        beginner.addLatihan(new RepetitionLatihan(
            "Jumping Jacks",
            "Jump with legs spread and hands overhead, then return",
            "Cardio, WarmUp",
            "WarmUp",
            1,
            20
        ));
        beginner.addLatihan(new DurationLatihan(
            "Neck Rolls",
            "Slowly roll your neck clockwise and counterclockwise",
            "Neck Mobility, WarmUp",
            "WarmUp",
            1,
            20
        ));
        beginner.addLatihan(new DurationLatihan(
            "Wall Sit",
            "Lean against a wall and sit as if on a chair",
            "Leg Strength, Endurance",
            "MainWorkout",
            2,
            30
        ));
        beginner.addLatihan(new RepetitionLatihan(
            "Squat",
            "Stand with feet shoulder-width, lower hips, return up",
            "Leg Strength, Glutes",
            "MainWorkout",
            3,
            15
        ));
        beginner.addLatihan(new RepetitionLatihan(
            "Push Up (knee)",
            "Knee push up for upper body strength",
            "Upper Body Strength, Core Stability",
            "MainWorkout",
            3,
            10
        ));
        beginner.addLatihan(new DurationLatihan(
            "Child Pose",
            "Kneel and stretch arms forward, relax back",
            "Stretching, Cooldown",
            "Cooldown",
            1,
            30
        ));
        beginner.addLatihan(new DurationLatihan(
            "Standing Stretch",
            "Stand and reach for your toes, hold",
            "Hamstring Stretch, Cooldown",
            "Cooldown",
            1,
            30
        ));
        Logic.allDifficulty.add(beginner);

        // Intermediate
        intermediate.addLatihan(new DurationLatihan(
            "High Knees",
            "Run in place bringing knees up high",
            "Cardio, WarmUp",
            "WarmUp",
            2,
            30
        ));
        intermediate.addLatihan(new RepetitionLatihan(
            "Arm Swings",
            "Swing arms forward and backward",
            "Shoulder Mobility, WarmUp",
            "WarmUp",
            1,
            20
        ));
        intermediate.addLatihan(new DurationLatihan(
            "Jump Rope",
            "Jump over imaginary rope",
            "Cardio, Coordination",
            "WarmUp",
            2,
            30
        ));
        intermediate.addLatihan(new DurationLatihan(
            "Plank",
            "Hold plank position on elbows",
            "Core Strength, Endurance",
            "MainWorkout",
            2,
            45
        ));
        intermediate.addLatihan(new RepetitionLatihan(
            "Squat Jump",
            "Squat then jump explosively",
            "Leg Power, Cardio",
            "MainWorkout",
            4,
            15
        ));
        intermediate.addLatihan(new RepetitionLatihan(
            "Push Up",
            "Standard push up for upper body",
            "Upper Body Strength, Core Stability",
            "MainWorkout",
            4,
            12
        ));
        intermediate.addLatihan(new DurationLatihan(
            "Cobra Stretch",
            "Lie face down, push chest up",
            "Back Stretch, Cooldown",
            "Cooldown",
            1,
            30
        ));
        intermediate.addLatihan(new DurationLatihan(
            "Seated Hamstring",
            "Sit and reach for toes",
            "Hamstring Stretch, Cooldown",
            "Cooldown",
            1,
            30
        ));
        Logic.allDifficulty.add(intermediate);

        // Advanced
        advanced.addLatihan(new RepetitionLatihan(
            "Jumping Lunges",
            "Lunge and jump to switch legs",
            "Leg Power, Cardio",
            "WarmUp",
            3,
            20
        ));
        advanced.addLatihan(new RepetitionLatihan(
            "Skater Jumps",
            "Jump side to side like a speed skater",
            "Lateral Power, Cardio",
            "WarmUp",
            3,
            20
        ));
        advanced.addLatihan(new RepetitionLatihan(
            "Burpees (slow)",
            "Full body movement, slow pace",
            "Full Body, Cardio",
            "WarmUp",
            4,
            10
        ));
        advanced.addLatihan(new RepetitionLatihan(
            "Burpees",
            "Full body movement, fast pace",
            "Full Body, Cardio",
            "MainWorkout",
            5,
            15
        ));
        advanced.addLatihan(new RepetitionLatihan(
            "Plank to Pushup",
            "Alternate plank and pushup positions",
            "Core, Upper Body",
            "MainWorkout",
            4,
            15
        ));
        advanced.addLatihan(new DurationLatihan(
            "Mountain Climbers",
            "Run in plank position, knees to chest",
            "Cardio, Core",
            "MainWorkout",
            3,
            45
        ));
        advanced.addLatihan(new DurationLatihan(
            "Forward Fold",
            "Stand and fold forward, relax",
            "Back/Hamstring Stretch, Cooldown",
            "Cooldown",
            1,
            30
        ));
        advanced.addLatihan(new DurationLatihan(
            "Downward Dog",
            "Yoga pose, hips up, stretch back/legs",
            "Full Body Stretch, Cooldown",
            "Cooldown",
            1,
            30
        ));
        Logic.allDifficulty.add(advanced);
    }
}

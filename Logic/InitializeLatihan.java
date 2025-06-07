package Logic;

import java.util.ArrayList;

import Model.DifficultyLatihan;
import Model.DurationLatihan;
import Model.RepetitionLatihan;

public class InitializeLatihan {
        public ArrayList<DifficultyLatihan> initializeLatihan() {
                ArrayList<DifficultyLatihan> allDifficulty = new ArrayList<>();
                DifficultyLatihan beginner = new DifficultyLatihan("Beginner");
                DifficultyLatihan intermediate = new DifficultyLatihan("Intermediate");
                DifficultyLatihan advanced = new DifficultyLatihan("Advanced");

                // Beginner
                // --- WARM UP ---
                beginner.addLatihan(
                                new DurationLatihan("Arm Circles", "Stand straight, extend arms, make small circles",
                                                "Shoulder Mobility, WarmUp", "WarmUp", 1, 30));
                beginner.addLatihan(new RepetitionLatihan("Jumping Jacks",
                                "Jump with legs spread and hands overhead, then return", "Cardio, WarmUp", "WarmUp", 1,
                                20));

                // --- MAIN WORKOUT ---
                beginner.addLatihan(new DurationLatihan("Wall Sit", "Lean against a wall and sit as if on a chair",
                                "Leg Strength, Endurance", "MainWorkout", 2, 30));
                beginner.addLatihan(
                                new RepetitionLatihan("Squat", "Stand with feet shoulder-width, lower hips, return up",
                                                "Leg Strength, Glutes", "MainWorkout", 3, 15));
                beginner.addLatihan(new RepetitionLatihan("Push Up (knee)", "Knee push up for upper body strength",
                                "Upper Body Strength, Core Stability", "MainWorkout", 3, 10));
                beginner.addLatihan(
                                new RepetitionLatihan("Glute Bridge",
                                                "Lie on your back, lift hips upward, squeeze glutes, lower down",
                                                "Glute Strength, Core Stability", "MainWorkout", 2, 12));
                beginner.addLatihan(new DurationLatihan("Marching in Place", "March in place, lifting knees high",
                                "Cardio, Leg Endurance", "MainWorkout", 1, 40));
                beginner.addLatihan(new RepetitionLatihan("Standing Calf Raise",
                                "Stand and lift heels off the ground, then lower", "Calf Strength, Balance",
                                "MainWorkout", 2, 15));
                beginner.addLatihan(new RepetitionLatihan("Step Up", "Step onto a low platform, alternate legs",
                                "Leg Strength",
                                "MainWorkout", 2, 12));
                beginner.addLatihan(new DurationLatihan("Seated Knee Extension", "Sit and extend knee, alternate legs",
                                "Leg Strength", "MainWorkout", 1, 30));
                beginner.addLatihan(new RepetitionLatihan("Wall Push Up", "Push up against wall", "Upper Body Strength",
                                "MainWorkout", 2, 15));
                beginner.addLatihan(new DurationLatihan("Standing Side Leg Raise", "Lift leg to side, alternate",
                                "Hip Strength", "MainWorkout", 1, 30));
                beginner.addLatihan(new DurationLatihan("Standing Hip Circle", "Circle hips clockwise/counterclockwise",
                                "Hip Mobility", "MainWorkout", 1, 20));

                // --- COOLDOWN ---
                beginner.addLatihan(new DurationLatihan("Child Pose", "Kneel and stretch arms forward, relax back",
                                "Stretching, Cooldown", "Cooldown", 1, 30));
                beginner.addLatihan(new DurationLatihan("Standing Stretch", "Stand and reach for your toes, hold",
                                "Hamstring Stretch, Cooldown", "Cooldown", 1, 30));
                allDifficulty.add(beginner);

                // Intermediate
                // --- WARM UP ---
                intermediate.addLatihan(new DurationLatihan("High Knees", "Run in place bringing knees up high",
                                "Cardio, WarmUp", "WarmUp", 2, 30));
                intermediate.addLatihan(new RepetitionLatihan("Arm Swings", "Swing arms forward and backward",
                                "Shoulder Mobility, WarmUp", "WarmUp", 1, 20));
                intermediate.addLatihan(
                                new DurationLatihan("Jump Rope", "Jump over imaginary rope", "Cardio, Coordination",
                                                "WarmUp", 2, 30));

                // --- MAIN WORKOUT ---
                intermediate.addLatihan(new DurationLatihan("Plank", "Hold plank position on elbows",
                                "Core Strength, Endurance", "MainWorkout", 2, 45));
                intermediate.addLatihan(
                                new RepetitionLatihan("Squat Jump", "Squat then jump explosively", "Leg Power, Cardio",
                                                "MainWorkout", 4, 15));
                intermediate.addLatihan(new RepetitionLatihan("Push Up", "Standard push up for upper body",
                                "Upper Body Strength, Core Stability", "MainWorkout", 4, 12));
                intermediate.addLatihan(
                                new RepetitionLatihan("Reverse Lunge", "Step backward into a lunge, alternate legs",
                                                "Leg Strength, Balance", "MainWorkout", 3, 16));
                intermediate.addLatihan(new DurationLatihan("Russian Twist", "Sit, lean back, twist torso side to side",
                                "Core Strength, Obliques", "MainWorkout", 2, 40));
                intermediate.addLatihan(new RepetitionLatihan("Triceps Dip (Chair)",
                                "Use a chair, lower and lift body with arms", "Triceps Strength, Upper Body",
                                "MainWorkout", 3, 12));
                intermediate.addLatihan(new RepetitionLatihan("Lateral Lunge", "Step to side, bend knee, alternate",
                                "Leg Strength", "MainWorkout", 3, 12));
                intermediate.addLatihan(
                                new DurationLatihan("Superman", "Lie face down, lift arms and legs", "Back Strength",
                                                "MainWorkout", 2, 30));
                intermediate.addLatihan(new RepetitionLatihan("Bicycle Crunch",
                                "Lie on back, pedal legs, touch elbows to knees", "Core Strength", "MainWorkout", 3,
                                20));
                intermediate.addLatihan(
                                new DurationLatihan("Bear Crawl", "Crawl forward and backward on hands and feet",
                                                "Full Body", "MainWorkout", 2, 30));
                intermediate.addLatihan(new RepetitionLatihan("Diamond Push Up", "Push up with hands close together",
                                "Triceps Strength", "MainWorkout", 2, 10));
                intermediate.addLatihan(new DurationLatihan("Flutter Kicks", "Lie on back, kick legs up and down",
                                "Core Strength", "MainWorkout", 2, 30));

                // --- COOLDOWN ---
                intermediate.addLatihan(new DurationLatihan("Cobra Stretch", "Lie face down, push chest up",
                                "Back Stretch, Cooldown", "Cooldown", 1, 30));
                intermediate.addLatihan(new DurationLatihan("Seated Hamstring", "Sit and reach for toes",
                                "Hamstring Stretch, Cooldown", "Cooldown", 1, 30));
                intermediate.addLatihan(
                                new DurationLatihan("Spinal Twist", "Lie on back, twist knees to side", "Spine Stretch",
                                                "Cooldown", 1, 30));
                allDifficulty.add(intermediate);

                // Advanced
                // --- WARM UP ---
                advanced.addLatihan(new RepetitionLatihan("Jumping Lunges", "Lunge and jump to switch legs",
                                "Leg Power, Cardio", "WarmUp", 3, 20));
                advanced.addLatihan(new RepetitionLatihan("Skater Jumps", "Jump side to side like a speed skater",
                                "Lateral Power, Cardio", "WarmUp", 3, 20));
                advanced.addLatihan(
                                new DurationLatihan("Jump Rope Double Under", "Jump rope, rope passes twice per jump",
                                                "Cardio, WarmUp", "WarmUp", 2, 30));
                advanced.addLatihan(new DurationLatihan("Dynamic Lunge Twist", "Lunge forward, twist torso, alternate",
                                "Leg/Core WarmUp", "WarmUp", 1, 20));

                // --- MAIN WORKOUT ---
                advanced.addLatihan(
                                new RepetitionLatihan("Burpees", "Full body movement, fast pace", "Full Body, Cardio",
                                                "MainWorkout", 5, 15));
                advanced.addLatihan(new RepetitionLatihan("Plank to Pushup", "Alternate plank and pushup positions",
                                "Core, Upper Body", "MainWorkout", 4, 15));
                advanced.addLatihan(new DurationLatihan("Mountain Climbers", "Run in plank position, knees to chest",
                                "Cardio, Core", "MainWorkout", 3, 45));
                advanced.addLatihan(new DurationLatihan("Side Plank",
                                "Hold plank position on one side, switch sides halfway",
                                "Core Strength, Obliques", "MainWorkout", 3, 40));
                advanced.addLatihan(new RepetitionLatihan("Pistol Squat (Assisted)",
                                "Single leg squat, use support if needed",
                                "Leg Strength, Balance", "MainWorkout", 4, 8));
                advanced.addLatihan(new DurationLatihan("Hollow Hold", "Lie on back, lift arms and legs, hold position",
                                "Core Strength, Endurance", "MainWorkout", 3, 45));
                advanced.addLatihan(
                                new RepetitionLatihan("Handstand Push Up (Assisted)", "Push up in handstand position",
                                                "Shoulder Strength", "MainWorkout", 3, 8));
                advanced.addLatihan(
                                new DurationLatihan("V-Up", "Lie on back, lift arms and legs to meet", "Core Strength",
                                                "MainWorkout", 2, 30));
                advanced.addLatihan(new RepetitionLatihan("Clapping Push Up", "Push up, clap hands at top",
                                "Explosive Upper Body", "MainWorkout", 3, 10));
                advanced.addLatihan(new DurationLatihan("Plank Jacks", "Plank position, jump feet in and out",
                                "Core/Cardio",
                                "MainWorkout", 2, 30));
                advanced.addLatihan(
                                new RepetitionLatihan("Single Leg Squat", "Squat on one leg, alternate", "Leg Strength",
                                                "MainWorkout", 3, 8));
                advanced.addLatihan(
                                new DurationLatihan("L-Sit Hold", "Sit, lift legs straight, hold", "Core/Arm Strength",
                                                "MainWorkout", 2, 20));
                advanced.addLatihan(
                                new RepetitionLatihan("Pull Up", "Hang and pull chin above bar", "Back/Arm Strength",
                                                "MainWorkout", 3, 8));
                advanced.addLatihan(
                                new DurationLatihan("Tuck Planche Hold", "Hold body off ground with arms, knees tucked",
                                                "Full Body Strength", "MainWorkout", 2, 15));
                advanced.addLatihan(new DurationLatihan("Reverse Plank", "Sit, lift hips, hold body straight",
                                "Back/Glute Strength", "MainWorkout", 2, 30));
                advanced.addLatihan(new RepetitionLatihan("Dragon Flag", "Lie on bench, lift body straight up",
                                "Core Strength",
                                "MainWorkout", 2, 6));

                // --- COOLDOWN ---
                advanced.addLatihan(new DurationLatihan("Forward Fold", "Stand and fold forward, relax",
                                "Back/Hamstring Stretch, Cooldown", "Cooldown", 1, 30));
                advanced.addLatihan(new DurationLatihan("Downward Dog", "Yoga pose, hips up, stretch back/legs",
                                "Full Body Stretch, Cooldown", "Cooldown", 1, 30));
                advanced.addLatihan(
                                new DurationLatihan("Happy Baby Pose", "Lie on back, hold feet, pull knees to chest",
                                                "Hip/Back Stretch", "Cooldown", 1, 30));
                advanced.addLatihan(
                                new DurationLatihan("Supine Twist", "Lie on back, twist knees to side", "Spine Stretch",
                                                "Cooldown", 1, 30));
                allDifficulty.add(advanced);

                return allDifficulty;
        }
}

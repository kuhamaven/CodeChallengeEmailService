package alicestudios.EmailService;

public class Constants
{
    //Time related constants
    public static final float Minute = 60;
    public static final float Hour = 60 * Minute;
    public static final float Day = 24 * Hour;

    //Combat related constants
    public static final float MinimalRandomBonus = 0.8f;
    public static final float MaximumRandomBonus = 1.15f;
    public static final int SpeedOffset = 5;
    public static final int BaseDodgeRate = 10;
    public static final float AttributeBonus = 0.25f;
    public static final int BaseCriticalChance = 5;
    public static final float BaseCriticalDamage = 1.75f;
    public static final float TiredStatPenalty = 0.7f;

    //Screen Boundaries for movement
    public static final float RightBoundary = 700f;
    public static final float LeftBoundary = -700f;

    //Hexa color codes
    public static final String RedHex = "#fe3535";
    public static final String BlueHex = "#066fc9";
    public static final String BlueDriver = "#35a1fe";
    public static final String RedDriver = "#f11a1a";
    public static final String YellowDriver = "#f3df38";
    public static final String GreenDriver = "#31bd2e";
    public static final String GreyDriver = "#FFFFFF";

    //Max equipped Drivers charge
    public static final int MaxDriversCharge = 50;

    //Time related variables from breeding aspect
    public static final float SecondsToPoop = Minute*15;
    public static final float SecondsToPoopAdult = Minute*20;
    public static final float SecondsToPoopPerfect = Minute*25;
    public static final float SecondsToAfkPoop = 5*Hour;
    public static final float SecondsToHunger = Minute*10;
    public static final float SecondsToAfkHunger = Hour;
    public static final float SecondsToSickFromHurt = 5*Minute;
    public static final float SecondsToYear = 3600;

    //Time in seconds for each evolutionary stage
    public static final float ToBabyII = 10 * Minute;
    public static final float ToChild = 15 * Minute;
    public static final float ToAdult = 20 * Hour;
    public static final float ToPerfect = Day;
    public static final float ToUltimate = 3 * Day;

    //Gained stats cap per stage
    public static final int BabyICap = 50;
    public static final int BabyIICap = 75 + BabyICap;
    public static final int ChildCap = 225 + BabyIICap;
    public static final int AdultCap = 300 + ChildCap;
    public static final int PerfectCap = 750 + AdultCap;

    //Training base energy cost
    public static final float TrainingCost = 10f;

    //Amount of stats that you inherit
    public static final float InheritedStats = 0.2f;

    //Base bits earned per victory
    public static final int BaseBitsReward = 100;

    public static final String RandomGeneratedUser = "analogman";
}
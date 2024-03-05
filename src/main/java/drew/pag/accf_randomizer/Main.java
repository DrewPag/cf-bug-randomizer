package drew.pag.accf_randomizer;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author drewpag
 */
public class Main {
    
    final static String[] bugs = new String[]{"Common Butterfly", "Yellow Butterfly", "Tiger Butterfly", "Peacock Butterfly",
        "Monarch Butterfly", "Emperor Butterfly", "Agrias Butterfly", "Raja Brooke", "Birdwing", "Moth", "Oak Silk Moth", "Honeybee",
        "Bee", "Long Locust", "Migratory Locust", "Mantis", "Orchid Mantis", "Brown Cicada", "Robust Cicada", "Walker Cicada",
        "Evening Cicada", "Lantern Fly", "Red Dragonfly", "Darner Dragonfly", "Banded Dragonfly", "Giant Petaltail", "Ant",
        "Pondskater", "Diving Beetle", "Snail", "Cricket", "Bell Cricket", "Grasshopper", "Mole Cricket", "Walking Leaf",
        "Walkingstick", "Bagworm", "Ladybug", "Violin Beetle", "Longhorn Beetle", "Dung Beetle", "Firefly", "Fruit Beetle",
        "Scarab Beetle", "Jewel Beetle", "Miyama Stag", "Saw Stag", "Giant Stag", "Rainbow Stag", "Cyclommatus", "Golden Stag",
        "Dynastid Beetle", "Atlas Beetle", "Elephant Beetle", "Hercules Beetle", "Goliath Beetle", "Flea", "Pill Bug", "Mosquito",
        "Fly", "Centipede", "Spider", "Tarantula", "Scorpion"};
    
    // I copied these from Ghidra, so they need to have the JP offset applied later...
    public static String[] JPN_BUG_BEHAVIOR_ADDRS = new String[]{
        "802a6c88", "802a6ca4", "802a6cc0", "802a6cdc", "802a6cf8", "802a6d14", "802a6d30", "802a6d4c", "802a6d68", "802a6d84", "802a6da0",
        "802a6dbc", "802a6dd8", "802a6df4", "802a6e10", "802a6e2c", "802a6e48", "802a6e64", "802a6e80", "802a6e9c", "802a6eb8", "802a6ed4",
        "802a6ef0", "802a6f0c", "802a6f28", "802a6f44", "802a6f60", "802a6f7c", "802a6f98", "802a6fb4", "802a6fd0", "802a6fec", "802a7008",
        "802a7024", "802a7040", "802a705c", "802a7078", "802a7094", "802a70b0", "802a70cc", "802a70e8", "802a7104", "802a7120", "802a713c",
        "802a7158", "802a7174", "802a7190", "802a71ac", "802a71c8", "802a71e4", "802a7200", "802a721c", "802a7238", "802a7254", "802a7270",
        "802a728c", "802a72a8", "802a72c4", "802a72e0", "802a72fc", "802a7318", "802a7334", "802a7350", "802a736c", "802a74cc"
    };
    
    // I copied these from Dolphin memory viewer, so no offset needed
    public static final String[] USA_BUG_BEHAVIOR_ADDRS = new String[]{
      "80EFF7EC", "80EFF808", "80EFF824", "80EFF840", "80EFF85C", "80EFF878", "80EFF894", "80EFF8B0", "80EFF8CC", "80EFF8E8", "80EFF904",
      "80EFF920", "80EFF93C", "80EFF958", "80EFF974", "80EFF990", "80EFF9AC", "80EFF9C8", "80EFF9E4", "80EFFA00", "80EFFA1C", "80EFFA38",
      "80EFFA54", "80EFFA70", "80EFFA8C", "80EFFAA8", "80EFFAC4", "80EFFAE0", "80EFFAFC", "80EFFB18", "80EFFB34", "80EFFB50", "80EFFB6C",
      "80EFFB88", "80EFFBA4", "80EFFBC0", "80EFFBDC", "80EFFBF8", "80EFFC14", "80EFFC30", "80EFFC4C", "80EFFC68", "80EFFC84", "80EFFCA0",
      "80EFFCBC", "80EFFCD8", "80EFFCF4", "80EFFD10", "80EFFD2C", "80EFFD48", "80EFFD64", "80EFFD80", "80EFFD9C", "80EFFDB8", "80EFFDD4",
      "80EFFDF0", "80EFFE0C", "80EFFE28", "80EFFE44", "80EFFE60", "80EFFE7C", "80EFFE98", "80EFFEB4", "80EFFED0"  
    };
    
    // I copied these from Ghidra, so they need to have the JP offset applied later...
    public static String[] JPN_BUG_SPAWN_CONDITION_SWITCH_ADDRS = new String[]{
        "80281bc4", "80281bc4", "80281bc4", "80281bc4", "80281bc4", "80281bc4", "80281bc4", "80281be4", "80281bc4", "80281bfc", "80281bac",
        "80281bc4", "80281c04", "80281bcc", "80281bcc", "80281bd4", "80281bd4", "80281bac", "80281bac", "80281bac", "80281bac", "80281bac",
        "80281bdc", "80281bdc", "80281bdc", "80281bdc", "80281c14", "80281be4", "80281be4", "80281bd4", "80281bcc", "80281bcc", "80281bcc",
        "80281bec", "80281bec", "80281bac", "80281bb4", "80281bd4", "80281c24", "80281c24", "80281c2c", "80281be4", "80281bac", "80281bac",
        "80281bac", "80281bac", "80281bac", "80281bac", "80281bac", "80281bbc", "80281bbc", "80281bac", "80281bbc", "80281bbc", "80281bbc",
        "80281bbc", "80281c1c", "80281bf4", "80281bdc", "80281c0c", "80281bf4", "80281bb4", "80281bec", "80281bec"
    };
    
    // I copied these from Dolphin memory viewer, so no offset needed
    public static final String[] USA_BUG_SPAWN_CONDITION_SWITCH_ADDRS = new String[]{
        "80EDA568", "80EDA568", "80EDA568", "80EDA568", "80EDA568", "80EDA568", "80EDA568", "80EDA588", "80EDA568", "80EDA5A0", "80EDA550",
        "80EDA568", "80EDA5A8", "80EDA570", "80EDA570", "80EDA578", "80EDA578", "80EDA550", "80EDA550", "80EDA550", "80EDA550", "80EDA550",
        "80EDA580", "80EDA580", "80EDA580", "80EDA580", "80EDA5B8", "80EDA588", "80EDA588", "80EDA578", "80EDA570", "80EDA570", "80EDA570",
        "80EDA590", "80EDA590", "80EDA550", "80EDA558", "80EDA578", "80EDA5C8", "80EDA5C8", "80EDA5D0", "80EDA588", "80EDA550", "80EDA550",
        "80EDA550", "80EDA550", "80EDA550", "80EDA550", "80EDA550", "80EDA560", "80EDA560", "80EDA550", "80EDA560", "80EDA560", "80EDA560",
        "80EDA560", "80EDA5C0", "80EDA598", "80EDA580", "80EDA5B0", "80EDA598", "80EDA558", "80EDA590", "80EDA590"
    };
    
    final static int JP_INSECT_REL_OFFSET = 0xc5cca4;
    final static int USA_INSECT_REL_OFFSET = 0xC58984;
    
    /**
     * 0, for bugs that fly anywhere (dragonflies + mosquito)
     * 1, for flying bugs that spawn from flowers
     * 2, for bugs that spawn on flowers (includes snail)
     * 3, for bugs that hop on the ground (hoppers + crickets)
     * 5, for bugs that need water (raja, diving beetle, pondskater, firefly)
     * 6, for ant (on rotten turnips or candy)
     * 7, for fly (flying near trash, rotten turnips, rafflesia)
     * 8, for bugs that walk on the ground (tarantula/scorpion, leaf, and mole cricket)
     * 9, for rock bugs
     * 10, for moth (flying near light source)
     * 11 (0xb), for stump bugs
     * 12 (0xc), for dung beetle (snowball)
     * 13 (0xd), for bugs in trees (spider + bagworm)
     * 14 (0xe), for coconut beetles
     * 15 (0xf), for bee + all bugs on trees
     * 17 (0x11), for flea ;_;
     */
    final static int[] bugSpawnConditionIds = new int[]{
        1, 1, 1, 1, 1, 1, 1, 5, 1, 10,
        0xf, 1, 0xf, 3, 3, 2, 2, 0xf, 0xf, 0xf,
        0xf, 0xf, 0, 0, 0, 0, 6, 5, 5, 2,
        3, 3, 3, 8, 8, 0xf, 0xd, 2, 0xb, 0xb,
        0xc, 5, 0xf, 0xf, 0xf, 0xf, 0xf, 0xf, 0xf, 0xe,
        0xe, 0xf, 0xe, 0xe, 0xe, 0xe, 0x11, 9, 0, 7,
        9, 0xd, 8, 8
    };
    
    /**
     * Each string describes the spawn condition for the spawn condition ID corresponding to its index
     */
    final static String[] bugSpawnConditions = new String[]{
        "Flying anywhere", "Flying from a flower", "On a flower", "Hopping on ground", "NOT USED", "In or on or above water",
        "On turnip/candy", "Flying above turnip/trash", "Walking on ground", "In a rock", "Flying near light source",
        "On a stump", "On a snowball", "In a tree", "On a coconut tree", "On a tree", "NOT USED", "On a villager"
    };
    
    /**
     * These bugs break things when given any behavior other than their vanilla behavior
     * 
     * Bee - 0xc
     * Ant - 0x1a
     * Flea - 0x38
     */
    final static int[] brokenBugIds = new int[] {0xc, 0x1a, 0x38};
    
    public final static String JPN_ALL_BUGS_ALL_YEAR_ALL_DAY_CODE = 
        """
        064ed4b0 00000030
        804ed300 804ed300
        804ed300 804ed300
        804ed300 804ed300
        804ed300 804ed300
        804ed300 804ed300
        804ed300 804ed300
        064ed300 00000030
        804ec6d8 0000003e
        804ec6d8 0000003e
        804ec6d8 0000003e
        804ec6d8 0000003e
        804ec6d8 0000003e
        804ec6d8 0000003e
        064ec6d8 00000100
        00000010 00010020
        00020030 00030040
        00040050 00050060
        00060070 00070080
        00080090 000900a0
        000a00b0 000b00c0
        000d00d0 000e00e0
        000f00f0 00100100
        00110110 00120120
        00130130 00140140
        00150150 00160160
        00170170 00180180
        00190190 001b01a0
        001c01b0 001d01c0
        001e01d0 001f01e0
        002001f0 00210200
        00220210 00230220
        00240230 00250240
        00260250 00270260
        00280270 00290280
        002a0290 002b02a0
        002c02b0 002d02c0
        002e02d0 002f02e0
        003002f0 00310300
        00320310 00330320
        00340330 00350340
        00360350 00370360
        00380370 00390380
        003a0390 003b03a0
        003c03b0 003d03c0
        003e03d0 003f03e0
        00000000 00000000
        E0000000 80008000""";
    
    public final static String USA_ALL_BUGS_ALL_YEAR_ALL_DAY_CODE = 
        """
        064e8650 00000030
        804e84a0 804e84a0
        804e84a0 804e84a0
        804e84a0 804e84a0
        804e84a0 804e84a0
        804e84a0 804e84a0
        804e84a0 804e84a0
        064e84a0 00000030
        804e7878 0000003e
        804e7878 0000003e
        804e7878 0000003e
        804e7878 0000003e
        804e7878 0000003e
        804e7878 0000003e
        064e7878 00000100
        00000010 00010020
        00020030 00030040
        00040050 00050060
        00060070 00070080
        00080090 000900a0
        000a00b0 000b00c0
        000d00d0 000e00e0
        000f00f0 00100100
        00110110 00120120
        00130130 00140140
        00150150 00160160
        00170170 00180180
        00190190 001b01a0
        001c01b0 001d01c0
        001e01d0 001f01e0
        002001f0 00210200
        00220210 00230220
        00240230 00250240
        00260250 00270260
        00280270 00290280
        002a0290 002b02a0
        002c02b0 002d02c0
        002e02d0 002f02e0
        003002f0 00310300
        00320310 00330320
        00340330 00350340
        00360350 00370360
        00380370 00390380
        003a0390 003b03a0
        003c03b0 003d03c0
        003e03d0 003f03e0
        00000000 00000000
        E0000000 80008000""";
    
    final static String JPN_ACTIVATOR = "20ed9880 00000074";
    final static String USA_ACTIVATOR = "20ed5580 00000074";
    
    static String region = "JPN";
    
    static Random random;
    static long seed;
    
    static String outputDir = "C:/Users/drewp/Documents/cf_bug_rando_output/";

    public static void main(String[] args) {
        
        applyJpnInsectRelOffset(JPN_BUG_BEHAVIOR_ADDRS);
        applyJpnInsectRelOffset(JPN_BUG_SPAWN_CONDITION_SWITCH_ADDRS);
        
        
//        seed = System.currentTimeMillis();
//        random = new Random(seed);
//        int[] ids = randomArray(64, random);
//        
//        System.out.println("seed: " + seed);
//        
//        printArray(ids);
//        
//        // force broken bugs to be vanilla
//        makeBrokenBugsVanilla(ids);
//        
//        // replace broken bug behaviors
//        replaceBrokenBugBehaviors(ids);
//        
//        // un-vanilla any non-broken vanilla mappings
//        unVanilla(ids);
//        
//        String behaviorCode = generateBehaviorCode(ids, region);
//        
//        System.out.println("Behavior code: \n" + behaviorCode + "\n");
//        
//        String spawnConditionCode = generateSpawnConditionCode(ids, region);
//        
//        System.out.println("Spawn condition code: \n" + spawnConditionCode + "\n");
//        
//        String bugRandoCode = generateBugRandoCode(ids, region);
//        System.out.println("seed: " + seed);
//        System.out.println("Master code: \n" + bugRandoCode);
//        
//        String bugMapping = getBugMapping(ids);
//        System.out.println(bugMapping);
//        
//        String spawnConditionTotals = getSpawnConditionTotals(ids);
//        System.out.println(spawnConditionTotals);
//        
//        writeOutputFile(bugRandoCode, seed, region, (region.equals("USA") ? "RUUE01.txt" : "RUUJ01.txt"));
//        writeOutputFile(bugMapping + "\n\n\n" + spawnConditionTotals, seed, region, "bug_mappings.txt");

        generateNCodes("USA", 100);
        generateNCodes("JPN", 100);
    }
    
    public static String generateBehaviorCode(int[] ids, String region){
        
        String[] behaviorAddrs = (region.equals("USA") ? USA_BUG_BEHAVIOR_ADDRS : JPN_BUG_BEHAVIOR_ADDRS);
        
        StringBuilder sb = new StringBuilder();
        
        // only activate this code when the insect .rel is loaded
        sb.append((region.equals("USA") ? USA_ACTIVATOR : JPN_ACTIVATOR)).append("\n");
        
        // starting at address 0x80f04030 write 0x100 = 256 bytes; 4 for each bug's address
        sb.append((region.equals("USA") ? "06EFFEF0 00000100" : "06F04030 00000100")).append("\n");
        
        for(int i = 0; i < ids.length; i += 2){
            sb.append(behaviorAddrs[ids[i]]).append(" ").append(behaviorAddrs[ids[i+1]]).append("\n");
//            if(ids.length - i > 2){
//                sb.append("\n");
//            }
        }
        
        // terminator
        sb.append("E0000000 80008000");
        
        return sb.toString();
    }
    
    public static String generateSpawnConditionCode(int[] ids, String region){
        
        String[] switchAddrs = (region.equals("USA") ? USA_BUG_SPAWN_CONDITION_SWITCH_ADDRS : JPN_BUG_SPAWN_CONDITION_SWITCH_ADDRS);
        
        StringBuilder sb = new StringBuilder();
        
        // only activate this code when the insect .rel is loaded
        sb.append((region.equals("USA") ? USA_ACTIVATOR : JPN_ACTIVATOR)).append("\n");
        
        // switch cases start addr = 80EFF648 (USA), 80F03788 (JPN), 
        sb.append((region.equals("USA") ? "06EFF648 00000100" : "06F03788 00000100")).append("\n");
        
        for(int i = 0; i < ids.length - 1; i += 2){
            sb.append(switchAddrs[ids[i]]).append(" ").append(switchAddrs[ids[i+1]]).append("\n");
        }
        
        // terminator
        sb.append("E0000000 80008000");
        
        return sb.toString();
    }
    
    public static String generateBugRandoCode(int[] ids, String region, long seed){
        StringBuilder sb = new StringBuilder();
        
        // header info seems necessary for programs like Ocarina's code manager to properly parse
        // idk if this is 100% correct but it works
        if(region.equals("USA")){
            sb.append("RUUE01").append("\n");
            sb.append("Animal Crossing - City Folk (USA, Asia) (En,Fr,Es)").append("\n\n");
        } else{
            sb.append("RUUJ01").append("\n");
            sb.append("Animal Crossing - City Folk (JPN) (Jp)").append("\n\n");
        }
        
        // Code title
        sb.append("Bug Randomizer (").append(region).append( ") (seed: ").append(seed).append(") [DrewPag]").append("\n");
        
        sb.append((region.equals("USA") ? USA_ALL_BUGS_ALL_YEAR_ALL_DAY_CODE : JPN_ALL_BUGS_ALL_YEAR_ALL_DAY_CODE)).append("\n");
        sb.append(generateBehaviorCode(ids, region)).append("\n");
        sb.append(generateSpawnConditionCode(ids, region));
        
        return sb.toString();
    }
    
    /**
     * Some bugs break things when given other behaviors... so we need to make them vanilla
     * 
     * @param ids - array of IDs to be modified
     */
    public static void makeBrokenBugsVanilla(int[] ids){
        
        for(int i: brokenBugIds){
            // if the index happened to roll the vanilla behavior, there's nothing to fix!
            if(ids[i] == i){
                return;
            }

            // find the index that contains the broken ID
            int brokenBehaviorIndex = 0;
            while(ids[brokenBehaviorIndex] != i){
                brokenBehaviorIndex++;
            }

            // replace the value at this index with the value that was generated for the broken index
            ids[brokenBehaviorIndex] = ids[i];

            // set the broken behavior index to vanilla
            ids[i] = i;
        }
    }
    
    /**
     * Some bug behaviors are broken when you try to apply them to other bugs.
     * Bee, Ant, and Flea behaviors are fixed already in another function. The bug behaviors we need to replace are:
     * Fly (0x3b 59) - fly behavior does work for other bugs, but the fly spawn requirement does not... so it would be a strange imbalance
     * 
     * @param ids - array of IDs to be modified
     * @param random - the random to use (necessary for generate100codes...
     */
    public static void replaceBrokenBugBehaviors(int[] ids, Random random){
        
        for(int i = 0; i < ids.length; i++){
            switch(ids[i]){
                
                // Fly
                case 0x3b:
                    // re-generate a random ID that is not one of the broken ones and is not flea
                    int newId = random.nextInt(64);
                    while(newId == 0xc || newId == 0x1a || newId == 0x3b || newId == 0x38){
                        newId = random.nextInt(64);
                    }
                    ids[i] = newId;
                    break;
            }
        }
    }
    
    public static void unVanilla(int[] ids, Random random){
        
        for(int i = 0; i < ids.length; i++){
            final int j = i;
            if(Arrays.stream(brokenBugIds).anyMatch(n -> n == j)){
                continue;
            }
            
            // if a non-broken ID is vanilla, swap it with something...
            if(ids[i] == i){
                System.out.println("Found vanilla mapping for id " + i);
                
                int rand = random.nextInt(64);
                while(rand == 0x1a || rand == 0x38){
                    rand = random.nextInt(64);
                }
                
                int temp = ids[rand];
                ids[rand] = ids[i];
                ids[i] = temp;
            }
        }
    }
    
    public static void applyJpnInsectRelOffset(String[] addrs){
        for(int i = 0; i < addrs.length; i++){
            long j = Long.parseLong(addrs[i], 16);
            j += JP_INSECT_REL_OFFSET;
            addrs[i] = Long.toHexString(j);
        }
    }
    
    public static int[] randomArray(int length, Random random){
        int[] result = new int[length];
        for(int i = 0; i < length; i++){
            result[i] = i;
        }
        
        for(int j = length - 1; j > 0; j--){
            int k = random.nextInt(j+1);
            int temp = result[j];
            result[j] = result[k];
            result[k] = temp;
        }
        
        return result;
    }
    
    public static String getBugMapping(int[] ids){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < ids.length; i++){
            sb.append(String.format("%1$18s", bugs[i])).append("\t->\t").append(bugs[ids[i]]).append("\n");
        }
        
        return sb.toString().substring(0, sb.length() - 1);
    }
    
    public static String getSpawnConditionTotals(int[] ids){
        int[] idCounts = new int[18];
        int[] vanillaCounts = new int[18];
        for(int x = 0; x < idCounts.length; x++){
            idCounts[x] = 0;
            vanillaCounts[x] = 0;
        }
        
        for(int i = 0; i < ids.length; i++){
            idCounts[bugSpawnConditionIds[ids[i]]] = idCounts[bugSpawnConditionIds[ids[i]]] + 1;
            vanillaCounts[bugSpawnConditionIds[i]] = vanillaCounts[bugSpawnConditionIds[i]] + 1;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Spawn Condition Totals:").append("\t\t").append("Generated").append("\t").append("Vanilla").append("\n\n");
        
        for(int j = 0; j < idCounts.length; j++){
            
            if(bugSpawnConditions[j].equals("NOT USED")){
                continue;
            }
            
            sb.append(String.format("%1$-24s", bugSpawnConditions[j])).append("\t").append(idCounts[j])
                    .append("\t\t").append(vanillaCounts[j]).append("\n");
        }
        
        return sb.toString().substring(0, sb.length() - 1);
    }
    
    public static void writeOutputFile(String data, long seed, String region, String fileName){
        Path output = Path.of(outputDir);
        
        Path regionSubDir = output.resolve(region + "/");
        if(!Files.exists(regionSubDir)){
            regionSubDir.toFile().mkdir();
        }
        
        Path seedSubDir = regionSubDir.resolve(seed + "/");
        if(!Files.exists(seedSubDir)){
            seedSubDir.toFile().mkdir();
        }
        
        File outputFile = seedSubDir.resolve(fileName).toFile();
        
        try (PrintWriter pw = new PrintWriter(outputFile)) {
            pw.print(data);
            pw.close();
        } catch(Exception ex){
            System.out.println("Exception writing file " + outputFile.toString());
            ex.printStackTrace();
        }
    }
    
    private static void generateNCodes(String region, int numCodes){
        
        for(int i = 0; i < numCodes; i++){
            
            long newSeed = System.currentTimeMillis();
            Random newRandom = new Random(newSeed);
            
            int[] ids = randomArray(64, newRandom);

            // force broken bugs to be vanilla
            makeBrokenBugsVanilla(ids);

            // replace broken bug behaviors
            replaceBrokenBugBehaviors(ids, newRandom);

            // un-vanilla any non-broken vanilla mappings
            unVanilla(ids, newRandom);
            
            String bugRandoCode = generateBugRandoCode(ids, region, newSeed);
            String bugMapping = getBugMapping(ids);
            String spawnConditionTotals = getSpawnConditionTotals(ids);
            
            writeOutputFile(bugRandoCode, newSeed, region, (region.equals("USA") ? "RUUE01.txt" : "RUUJ01.txt"));
            writeOutputFile(bugMapping + "\n\n\n" + spawnConditionTotals, newSeed, region, "bug_mappings.txt");
            
            System.out.println("Generated code #" + (i+1) + " for region " + region);
        }
    }
    
    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}

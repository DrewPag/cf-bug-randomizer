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
    
    public static String[] bugBehaviorGhidraAddrs = new String[]{
        "802a6c88", "802a6ca4", "802a6cc0", "802a6cdc", "802a6cf8", "802a6d14", "802a6d30", "802a6d4c", "802a6d68", "802a6d84", "802a6da0",
        "802a6dbc", "802a6dd8", "802a6df4", "802a6e10", "802a6e2c", "802a6e48", "802a6e64", "802a6e80", "802a6e9c", "802a6eb8", "802a6ed4",
        "802a6ef0", "802a6f0c", "802a6f28", "802a6f44", "802a6f60", "802a6f7c", "802a6f98", "802a6fb4", "802a6fd0", "802a6fec", "802a7008",
        "802a7024", "802a7040", "802a705c", "802a7078", "802a7094", "802a70b0", "802a70cc", "802a70e8", "802a7104", "802a7120", "802a713c",
        "802a7158", "802a7174", "802a7190", "802a71ac", "802a71c8", "802a71e4", "802a7200", "802a721c", "802a7238", "802a7254", "802a7270",
        "802a728c", "802a72a8", "802a72c4", "802a72e0", "802a72fc", "802a7318", "802a7334", "802a7350", "802a736c", "802a74cc"
    };
    
    public static String[] bugSpawnConditionSwitchCaseGhidraAddrs = new String[]{
        "80281bc4", "80281bc4", "80281bc4", "80281bc4", "80281bc4", "80281bc4", "80281bc4", "80281be4", "80281bc4", "80281bfc", "80281bac",
        "80281bc4", "80281c04", "80281bcc", "80281bcc", "80281bd4", "80281bd4", "80281bac", "80281bac", "80281bac", "80281bac", "80281bac",
        "80281bdc", "80281bdc", "80281bdc", "80281bdc", "80281c14", "80281be4", "80281be4", "80281bd4", "80281bcc", "80281bcc", "80281bcc",
        "80281bec", "80281bec", "80281bac", "80281bb4", "80281bd4", "80281c24", "80281c24", "80281c2c", "80281be4", "80281bac", "80281bac",
        "80281bac", "80281bac", "80281bac", "80281bac", "80281bac", "80281bbc", "80281bbc", "80281bac", "80281bbc", "80281bbc", "80281bbc",
        "80281bbc", "80281c1c", "80281bf4", "80281bdc", "80281c0c", "80281bf4", "80281bb4", "80281bec", "80281bec"
    };
    
    final static int INSECT_REL_OFFSET = 0xc5cca4;
    
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
     * Ant - 0x1a
     * Flea - 0x38
     */
    final static int[] brokenBugIds = new int[] {0x1a, 0x38};
    
    public final static String ALL_BUGS_ALL_YEAR_ALL_DAY_CODE = 
        """
        064ed4b0 00000030
        804ed300 804ed300
        804ed300 804ed300
        804ed300 804ed300
        804ed300 804ed300
        804ed300 804ed300
        804ed300 804ed300
        064ed300 00000030
        804ec6d8 0000003f
        804ec6d8 0000003f
        804ec6d8 0000003f
        804ec6d8 0000003f
        804ec6d8 0000003f
        804ec6d8 0000003f
        064ec6d8 00000100
        0000000f 0001001e
        0002002d 0003003c
        0004004b 0005005a
        00060069 00070078
        00080087 00090096
        000a00a5 000b00b4
        000c00c3 000d00d2
        000e00e1 000f00f0
        001000ff 0011010e
        0012011d 0013012c
        0014013b 0015014a
        00160159 00170168
        00180177 00190186
        001b0195 001c01a4
        001d01b3 001e01c2
        001f01d1 002001e0
        002101ef 002201fe
        0023020d 0024021c
        0025022b 0026023a
        00270249 00280258
        00290267 002a0276
        002b0285 002c0294
        002d02a3 002e02b2
        002f02c1 003002d0
        003102df 003202ee
        003302fd 0034030c
        0035031b 0036032a
        00370339 00380348
        00390357 003a0366
        003b0375 003c0384
        003d0393 003e03a2
        003f03b1 00000000
        E0000000 80008000""";
    
    static Random random;
    
    static String outputDir = "C:/Users/drewp/Documents/cf_bug_rando_output/";

    public static void main(String[] args) {
        
        applyInsectRelOffset(bugBehaviorGhidraAddrs);
        applyInsectRelOffset(bugSpawnConditionSwitchCaseGhidraAddrs);
        
        long seed = System.currentTimeMillis();
        random = new Random(seed);
        int[] ids = randomArray(64);
        
        System.out.println("seed: " + seed);
        
        printArray(ids);
        
        // force broken bugs to be vanilla
        makeBrokenBugsVanilla(ids);
        
        // replace broken bug behaviors
        replaceBrokenBugBehaviors(ids);
        
        // un-vanilla any non-broken vanilla mappings
        unVanilla(ids);
        
        String behaviorCode = generateBehaviorCode(ids);
        
        System.out.println("Behavior code: \n" + behaviorCode + "\n");
        
        String spawnConditionCode = generateSpawnConditionCode(ids);
        
        System.out.println("Spawn condition code: \n" + spawnConditionCode + "\n");
        
        String bugRandoCode = generateBugRandoCode(ids);
        System.out.println("seed: " + seed);
        System.out.println("Master code: \n" + bugRandoCode);
        
        String bugMapping = getBugMapping(ids);
        System.out.println(bugMapping);
        
        String spawnConditionTotals = getSpawnConditionTotals(ids);
        System.out.println(spawnConditionTotals);
        
        writeOutputFile(bugRandoCode, seed, "RUUJ01.txt");
        writeOutputFile(bugMapping, seed, "bug_mappings.txt");
        writeOutputFile(spawnConditionTotals, seed, "spawn_conditions.txt");
    }
    
    public static String generateBehaviorCode(int[] ids){
        StringBuilder sb = new StringBuilder();
        
        // only activate this code when the insect .rel is loaded
        sb.append("20f01cb4 00b30014").append("\n");
        
        // starting at address 0x80f04030 write 0x100 = 256 bytes; 4 for each bug's address
        sb.append("06F04030 00000100").append("\n");
        
        for(int i = 0; i < ids.length; i += 2){
            sb.append(bugBehaviorGhidraAddrs[ids[i]]).append(" ").append(bugBehaviorGhidraAddrs[ids[i+1]]).append("\n");
//            if(ids.length - i > 2){
//                sb.append("\n");
//            }
        }
        
        // terminator
        sb.append("E0000000 80008000");
        
        return sb.toString();
    }
    
    public static String generateSpawnConditionCode(int[] ids){
        StringBuilder sb = new StringBuilder();
        
        // only activate this code when the insect .rel is loaded
        sb.append("20f01cb4 00b30014").append("\n");
        
        // Ghidra address 802a6ae4 = 80F03788 in RAM is the start of the switch cases
        sb.append("06F03788 00000100").append("\n");
        
        for(int i = 0; i < ids.length - 1; i += 2){
            sb.append(bugSpawnConditionSwitchCaseGhidraAddrs[ids[i]]).append(" ")
                    .append(bugSpawnConditionSwitchCaseGhidraAddrs[ids[i+1]]).append("\n");
//            if(ids.length - i > 2){
//                sb.append("\n");
//            }
        }
        
        // terminator
        sb.append("E0000000 80008000");
        
        return sb.toString();
    }
    
    public static String generateBugRandoCode(int[] ids){
        StringBuilder sb = new StringBuilder();
        
        sb.append(ALL_BUGS_ALL_YEAR_ALL_DAY_CODE).append("\n");
        sb.append(generateBehaviorCode(ids)).append("\n");
        sb.append(generateSpawnConditionCode(ids));
        
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
     * Flea and Ant behaviors are fixed already in another function. The bug behaviors we need to replace are:
     * Bee (0xc 12) - it actually works great, the bug becomes a swarm of bees and chases you... but once you catch them,
     *  more swarms continuously chase you until you get stung... and even then, the bee panic music never ends....
     * Fly (0x3b 59) - fly behavior does work for other bugs, but the fly spawn requirement does not... so it would be a strange imbalance
     * 
     * @param ids - array of IDs to be modified
     */
    public static void replaceBrokenBugBehaviors(int[] ids){
        
        for(int i = 0; i < ids.length; i++){
            switch(ids[i]){
                
                // Bee
                case 0xc:
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
    
    public static void unVanilla(int[] ids){
        
        for(int i = 0; i < ids.length; i++){
            final int j = i;
            if(Arrays.stream(brokenBugIds).anyMatch(n -> n == j)){
                continue;
            }
            
            // if a non-broken ID is vanilla, swap it with something...
            if(ids[i] == i){
                System.out.println("Found vanilla mapping for id " + i);
                
                int rand = random.nextInt(64);
                final int c = rand;
                while(Arrays.stream(brokenBugIds).anyMatch(n -> n == c)){
                    rand = random.nextInt(64);
                }
                
                int temp = ids[rand];
                ids[rand] = ids[i];
                ids[i] = temp;
            }
        }
    }
    
    public static void applyInsectRelOffset(String[] addrs){
        for(int i = 0; i < addrs.length; i++){
            long j = Long.parseLong(addrs[i], 16);
            j += INSECT_REL_OFFSET;
            addrs[i] = Long.toHexString(j);
        }
    }
    
    public static int[] randomArray(int length){
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
    
    public static void writeOutputFile(String data, long seed, String fileName){
        Path output = Path.of(outputDir);
        Path subDir = output.resolve(seed + "/");
        if(!Files.exists(subDir)){
            subDir.toFile().mkdir();
        }
        
        File outputFile = subDir.resolve(fileName).toFile();
        
        try (PrintWriter pw = new PrintWriter(outputFile)) {
            pw.print(data);
        } catch(Exception ex){
            System.out.println("Exception writing file " + outputFile.toString());
            ex.printStackTrace();
        }
    }
    
    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}

package net.aakagure.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class VilinhaHauteCoutureGenerators {

    public static void main(String[] args) {
        if (args.length < 7) {
            System.err.println("Argumentos insuficientes. Necessário: blockstatesPath, itemModelsPath, langFilePath, lootTablesPath, additionalBlockstatesPath, additionalBlockModelsPath, additionalItemModelsPath");
            return;
        }

        String blockstatesPath = args[0];
        String itemModelsPath = args[1];
        String langFilePath = args[2];
        String lootTablesPath = args[3];
        String additionalBlockstatesPath = args[4];
        String additionalBlockModelsPath = args[5];
        String additionalItemModelsPath = args[6];

        try {
            generateResources(blockstatesPath, itemModelsPath, langFilePath, lootTablesPath, additionalBlockstatesPath, additionalBlockModelsPath, additionalItemModelsPath);
            cleanupResources(blockstatesPath, itemModelsPath, langFilePath, lootTablesPath, additionalBlockstatesPath, additionalItemModelsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateResources(String blockstatesPath, String itemModelsPath, String langFilePath, String lootTablesPath, String additionalBlockstatesPath, String additionalBlockModelsPath, String additionalItemModelsPath) throws IOException {
        Path langPath = Paths.get(langFilePath);

        // Verifica se o arquivo de linguagem existe antes de ler
        if (!Files.exists(langPath)) {
            System.err.println("Arquivo de linguagem não encontrado: " + langFilePath);
            return;
        }

        String langContent = new String(Files.readAllBytes(langPath));
        Gson gson = new Gson();

        // Lê o JSON do arquivo de linguagem
        Map<String, String> langMap = gson.fromJson(langContent, (new TypeToken<HashMap<String, String>>() {
        }).getType());

        for (String key : langMap.keySet()) {
            // Filtra apenas as chaves que começam com "block.cocricot."
            if (key.startsWith("block.cocricot.")) {
                String blockName = key.substring("block.cocricot.".length());

                // 1. Gera Blockstate Básico
                Path blockstateFile = Paths.get(blockstatesPath, blockName + ".json");
                if (!Files.exists(blockstateFile, new LinkOption[0])) {
                    String blockstateContent = "{ \"variants\": { \"\": { \"model\": \"cocricot:block/block\" }}}";
                    Files.write(blockstateFile, blockstateContent.getBytes(), new OpenOption[]{StandardOpenOption.CREATE});
                }

                // 2. Gera Item Model Básico
                Path itemModelFile = Paths.get(itemModelsPath, blockName + ".json");
                if (!Files.exists(itemModelFile, new LinkOption[0])) {
                    String itemModelContent = "{ \"parent\": \"cocricot:block/block\" }";
                    Files.write(itemModelFile, itemModelContent.getBytes(), new OpenOption[]{StandardOpenOption.CREATE});
                }

                // 3. Gera Loot Table Básica
                Path lootTableFile = Paths.get(lootTablesPath, blockName + ".json");
                if (!Files.exists(lootTableFile, new LinkOption[0])) {
                    String lootTableContent = "{\n  \"type\": \"minecraft:block\",\n  \"pools\": [\n    {\n      \"rolls\": 1.0,\n      \"entries\": [\n        {\n          \"type\": \"minecraft:item\",\n          \"name\": \"cocricot:" + blockName + "\"\n        }\n      ]\n    }\n  ]\n}";
                    if (lootTableFile.getParent() != null) {
                        Files.createDirectories(lootTableFile.getParent());
                    }
                    Files.write(lootTableFile, lootTableContent.getBytes(), new OpenOption[]{StandardOpenOption.CREATE});
                }

                // 4. Gera Blockstates Adicionais (Lógica para Panes/Vidros)
                Path additionalBlockstateFile = Paths.get(additionalBlockstatesPath, blockName + ".json");
                if (!Files.exists(additionalBlockstateFile, new LinkOption[0])) {
                    String blockstateContent;
                    // Lógica complexa para painéis de vidro (panes)
                    if (blockName.contains("_pane")) {
                        blockstateContent = "{ \"variants\": { \"east=false,north=false,south=false,west=false\": { \"model\": \"cocricot:block/" + blockName + "_post\" }, \"east=false,north=true,south=false,west=false\": { \"model\": \"cocricot:block/" + blockName + "_side\", \"y\": 90 }, \"east=true,north=false,south=false,west=false\": { \"model\": \"cocricot:block/" + blockName + "_side\", \"y\": 180 }, \"east=false,north=false,south=true,west=false\": { \"model\": \"cocricot:block/" + blockName + "_side\", \"y\": 270 }, \"east=false,north=false,south=false,west=true\": { \"model\": \"cocricot:block/" + blockName + "_side\" }, \"east=true,north=true,south=false,west=false\": { \"model\": \"cocricot:block/" + blockName + "_slant\", \"y\": 180 }, \"east=true,north=false,south=true,west=false\": { \"model\": \"cocricot:block/" + blockName + "_slant\", \"y\": 270 }, \"east=false,north=false,south=true,west=true\": { \"model\": \"cocricot:block/" + blockName + "_slant\" }, \"east=false,north=true,south=false,west=true\": { \"model\": \"cocricot:block/" + blockName + "_slant\", \"y\": 90 }, \"east=false,north=true,south=true,west=false\": { \"model\": \"cocricot:block/" + blockName + "\", \"y\": 90 },\"east=true,north=false,south=false,west=true\": { \"model\": \"cocricot:block/" + blockName + "\" },\"east=true,north=true,south=true,west=false\": { \"model\": \"cocricot:block/" + blockName + "_three\", \"y\": 270 },\"east=true,north=false,south=true,west=true\": { \"model\": \"cocricot:block/" + blockName + "_three\" },\"east=false,north=true,south=true,west=true\": { \"model\": \"cocricot:block/" + blockName + "_three\", \"y\": 90 },\"east=true,north=true,south=false,west=true\": { \"model\": \"cocricot:block/" + blockName + "_three\", \"y\": 180 },\"east=true,north=true,south=true,west=true\": { \"model\": \"cocricot:block/" + blockName + "_all\" }}}";
                    } else {
                        blockstateContent = "{ \"variants\": { \"\": { \"model\": \"cocricot:block/" + blockName + "\" }}}";
                    }
                    Files.write(additionalBlockstateFile, blockstateContent.getBytes(), new OpenOption[]{StandardOpenOption.CREATE});
                }

                // 5. Gera Block Models Adicionais
                Path additionalBlockModelFile = Paths.get(additionalBlockModelsPath, blockName + ".json");
                if (!Files.exists(additionalBlockModelFile, new LinkOption[0])) {
                    String blockModelContent = "{ \"parent\": \"block/cube_all\", \"textures\": { \"all\": \"cocricot:block/" + blockName + "\" }}";
                    Files.write(additionalBlockModelFile, blockModelContent.getBytes(), new OpenOption[]{StandardOpenOption.CREATE});
                }

                // 6. Gera Item Models Adicionais
                Path additionalItemModelFile = Paths.get(additionalItemModelsPath, blockName + ".json");
                if (!Files.exists(additionalItemModelFile, new LinkOption[0])) {
                    String itemModelContent = "{ \"parent\": \"cocricot:block/" + blockName + "\" }";
                    Files.write(additionalItemModelFile, itemModelContent.getBytes(), new OpenOption[]{StandardOpenOption.CREATE});
                }
            }
        }

        // --- Lógica de Limpeza de Arquivos Adicionais ---
        Map<String, Path> existingAdditionalBlockstates = new HashMap<>();
        Map<String, Path> existingAdditionalItemModels = new HashMap<>();

        try (Stream<Path> stream = Files.walk(Paths.get(additionalBlockstatesPath))) {
            stream.filter((path) -> Files.isRegularFile(path, new LinkOption[0]))
                    .forEach((file) -> existingAdditionalBlockstates.put(file.getFileName().toString(), file));
        }

        try (Stream<Path> stream = Files.walk(Paths.get(additionalItemModelsPath))) {
            stream.filter((path) -> Files.isRegularFile(path, new LinkOption[0]))
                    .forEach((file) -> existingAdditionalItemModels.put(file.getFileName().toString(), file));
        }

        for (String fileName : existingAdditionalBlockstates.keySet()) {
            if (!langMap.containsKey("block.cocricot." + fileName.substring(0, fileName.lastIndexOf('.')))) {
                Files.delete(existingAdditionalBlockstates.get(fileName));
            }
        }

        for (String fileName : existingAdditionalItemModels.keySet()) {
            if (!langMap.containsKey("block.cocricot." + fileName.substring(0, fileName.lastIndexOf('.')))) {
                Files.delete(existingAdditionalItemModels.get(fileName));
            }
        }
    }

    public static void cleanupResources(String blockstatesPath, String itemModelsPath, String langFilePath, String lootTablesPath, String additionalBlockstatesPath, String additionalItemModelsPath) throws IOException {
        Path langPath = Paths.get(langFilePath);
        if (!Files.exists(langPath)) return;

        String langContent = new String(Files.readAllBytes(langPath));
        Gson gson = new Gson();
        Map<String, String> langMap = gson.fromJson(langContent, (new TypeToken<HashMap<String, String>>() {
        }).getType());

        Map<String, Path> existingBlockstates = new HashMap<>();
        Map<String, Path> existingItemModels = new HashMap<>();

        try (Stream<Path> stream = Files.walk(Paths.get(blockstatesPath))) {
            stream.filter((path) -> Files.isRegularFile(path, new LinkOption[0]))
                    .forEach((file) -> existingBlockstates.put(file.getFileName().toString(), file));
        }

        try (Stream<Path> stream = Files.walk(Paths.get(itemModelsPath))) {
            stream.filter((path) -> Files.isRegularFile(path, new LinkOption[0]))
                    .forEach((file) -> existingItemModels.put(file.getFileName().toString(), file));
        }

        // Deleta blockstates que não estão no arquivo de tradução
        for (String fileName : existingBlockstates.keySet()) {
            if (!langMap.containsKey("block.cocricot." + fileName.substring(0, fileName.lastIndexOf('.')))) {
                Files.delete(existingBlockstates.get(fileName));
            }
        }

        // Deleta item models que não estão no arquivo de tradução
        for (String fileName : existingItemModels.keySet()) {
            if (!langMap.containsKey("block.cocricot." + fileName.substring(0, fileName.lastIndexOf('.')))) {
                Files.delete(existingItemModels.get(fileName));
            }
        }

        Map<String, Path> existingLootTables = new HashMap<>();
        try (Stream<Path> stream = Files.walk(Paths.get(lootTablesPath))) {
            stream.filter((path) -> Files.isRegularFile(path, new LinkOption[0]))
                    .forEach((file) -> existingLootTables.put(file.getFileName().toString(), file));
        }

        // Deleta loot tables que não estão no arquivo de tradução
        for (String fileName : existingLootTables.keySet()) {
            if (!langMap.containsKey("block.cocricot." + fileName.substring(0, fileName.lastIndexOf('.')))) {
                Files.delete(existingLootTables.get(fileName));
            }
        }
    }
}
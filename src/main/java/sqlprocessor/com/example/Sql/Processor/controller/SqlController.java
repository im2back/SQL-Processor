package sqlprocessor.com.example.Sql.Processor.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/sql")
public class SqlController {

    private static final int MAX_LINES_PER_FILE = 1000;  // Máximo de linhas por arquivo
    private static final String CONSTANT_NAME = "raiaprod-R102_X8";  // Constante da nomenclatura

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonList("Arquivo vazio!"), HttpStatus.BAD_REQUEST);
        }

        try {
            List<String> lines = extractSqlLines(file);  // Extrai linhas de comandos SQL
            List<String> fileNames = splitSqlLines(lines);  // Divide as linhas em vários arquivos
            return new ResponseEntity<>(fileNames, HttpStatus.OK);  // Retorna os nomes dos arquivos gerados
        } catch (IOException e) {
            return new ResponseEntity<>(Collections.singletonList("Erro ao processar o arquivo"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Extrai cada linha do script SQL, incluindo o delimitador "/"
    private List<String> extractSqlLines(MultipartFile file) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());  // Adiciona cada linha, removendo espaços extras
            }
        }
        return lines;
    }

    // Divide as linhas em arquivos com no máximo 1000 linhas cada (incluindo "/")
    private List<String> splitSqlLines(List<String> lines) throws IOException {
        List<String> fileNames = new ArrayList<>();
        int fileCount = 1;
        int totalFiles = (int) Math.ceil((double) lines.size() / MAX_LINES_PER_FILE);  // Calcula o número total de arquivos pode colocar valor fixo também 

        // Diretório de saída (ajuste conforme necessário)
        String outputDirectory = "C:/Users/SEU-USUARIO/Desktop/NOME_DA_PASTA_DE_SAÍDA/";
        Files.createDirectories(Paths.get(outputDirectory));  // Cria o diretório se não existir

        for (int i = 0; i < lines.size(); i += MAX_LINES_PER_FILE) {
            // Formata o nome do arquivo seguindo a nomenclatura solicitada
            String fileName = String.format("%02d-%02d-%s.sql", fileCount, totalFiles, CONSTANT_NAME);
            String fullPath = outputDirectory + fileName;
            fileNames.add(fullPath);

            // Escreve as linhas no arquivo
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fullPath))) {
                for (int j = i; j < i + MAX_LINES_PER_FILE && j < lines.size(); j++) {
                    writer.write(lines.get(j));
                    if (j < i + MAX_LINES_PER_FILE - 1 && j < lines.size() - 1) {
                        writer.newLine();  // Adiciona nova linha apenas entre linhas, não após a última
                    }
                }
            }
            fileCount++;
        }
        return fileNames;
    }
}

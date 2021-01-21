package br.com.eduardo.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.eduardo.config.FileStorageConfig;
import br.com.eduardo.exception.FileStorageException;
import br.com.eduardo.exception.MyFileNotFoundException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		// Busca o caminho para salvar arquivos da classe de configurações e o normaliza
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

		// Tenta criar o diretório na construção da classe
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the files will be stored", e);
		}
	}

	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Filename contains invalid path sequence " + fileName);
			}

			// Busca o caminho onde os arquivos serão guardados e inclui o nome do arquivo a
			// ser salvo
			Path targetLocation = this.fileStorageLocation.resolve(fileName);

			// Copia o array de bytes do arquivo recebido para o local a ser salvo, dando
			// replace se arquivo já existir
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (Exception e) {
			throw new FileStorageException("Could not store file " + fileName + ". Try again.", e);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			// Busca o caminho onde os arquivos serão guardados busca o arquivo
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			
			// Ler o arquivo como resource
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found." + fileName);
			}
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found." + fileName, e);
		}
	}

}

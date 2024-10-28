
Dividir um bloco de comandos em arquivos menores


## 1 - Ajuste o diretório de saída para sua área de trabalho 
- Ajuste o diretório e de um nome para a pasta que armazenará os arquivos de saída

![image](https://github.com/user-attachments/assets/7524da48-fbd5-42d9-a04c-669da5230fe2)

##  2- Ajuste as constantes de 


Nome e Número de linhas por aquivo :
![image](https://github.com/user-attachments/assets/a3bd084e-2748-4307-a85d-9b0a1a96b257)


Contagem inicial e número total de arquivos que serão gerados
![image](https://github.com/user-attachments/assets/81c0010a-809b-4226-b9fb-8d86d2e52e13)

### Requisição utilizando PowerShell 
- Nesta requisição estamos informando onde se encontra o arquivo que será processado e informando o endereço do endpoint, pode ser necessario mudar o local do arquivo com base no seu diretorio.
```
cmd /c curl -F "file=@C:\Users\SEU-USUARIO\Desktop\NOME_DO_ARQUIVO.txt" http://localhost:8080/api/sql/upload
```

## Exemplo de sucesso
![image](https://github.com/user-attachments/assets/7ed843ac-7755-4120-ba05-4f4b5afa0211)




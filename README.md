# desafio-goa

Endpoints:

# Pontos de venda por ID
http://localhost:8080/pdv/{id}

# Pesquisa completa de Pontos de venda paginado com os parametros nome, cidade, cep, page (qual página iniciando em 0), linesPerPage (quantas linhas por pagina), orderBy e direction (ASC ou DESC)

http://localhost:8080/pdv/search

Exemplo:
http://localhost:8080/pdv/search?nome=a&cidade=2&cep=00000000&page=0&linesPerPage=50&orderBy=nome&direction=ASC

# Processar o arquivo CSV
http://localhost:8080/pdv/processa

Exemplo:

curl -X POST \
  http://localhost:8080/pdv/processa \
  -H 'cache-control: no-cache' \
  -H 'content-type: multipart/form-data' \
  -F data=@pdv_list.csv

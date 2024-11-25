@Composable
fun ConsultarScreen(produtos: List<Produto>) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Consultar Produtos") })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(produtos) { produto ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        if (produto.imagemUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(produto.imagemUri),
                                contentDescription = "Imagem do Produto",
                                modifier = Modifier.size(100.dp),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text("Sem Imagem")
                        }

                        Column {
                            Text(text = "Nome: ${produto.nome}")
                            Text(text = "Preço: R$ ${produto.preco}")
                            Text(text = "Categoria: ${produto.categoria}")
                            Text(text = "Descrição: ${produto.descricao}")

                        }
                    }
                }
            }
        }
    }
}
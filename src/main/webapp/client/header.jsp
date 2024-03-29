<header class="row w-100 mx-0" >
        <nav class="navbar navbar-expand-sm navbar-light bg-light w-100">
            <div class="container-fluid">
              <a class="navbar-brand" href="index.jsp">Hermes Store</a>
              <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                <form class="form-inline my-2 my-lg-0 mx-auto" method="get" action="index.jsp">
                  <input class="form-control mr-sm-2" type="search" name="nomP" placeholder="Rechercher produit" aria-label="Search">
                  <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Rechercher</button>
                </form>
                <ul class="navbar-nav align-items-center">
                  <li class="nav-item active ">
                    <a class="nav-link" href="index.jsp">Catalogue</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="panier.jsp">
                        <i class="bx text-primary bx-cart-add icon-large"></i>
                    </a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="listCommandes.jsp">Mes commandes</a>
                  </li>
                  <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      <i class="bx text-primary bx-user-circle icon-large" ></i>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                      <a class="dropdown-item" href="profile.jsp">Profil</a>
                      <a class="dropdown-item" href="/HermesStore/LogoutServlet">D&eacute;connexion</a>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </nav>
    </header>
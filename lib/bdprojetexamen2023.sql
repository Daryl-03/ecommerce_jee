-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 29 mars 2024 à 23:35
-- Version du serveur : 10.4.24-MariaDB
-- Version de PHP : 8.1.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bdprojetexamen2023`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `id` int(11) NOT NULL,
  `nom` varchar(40) NOT NULL,
  `description` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`id`, `nom`, `description`) VALUES
(1, 'Mobilier', ''),
(2, 'Alimentation', ''),
(3, 'Cosmétique', ''),
(4, 'Accessoire', '');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `nom` varchar(40) NOT NULL,
  `type` varchar(40) NOT NULL,
  `adresse` varchar(40) NOT NULL,
  `telephone` varchar(40) NOT NULL,
  `specialite` varchar(40) DEFAULT NULL,
  `id_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `nom`, `type`, `adresse`, `telephone`, `specialite`, `id_user`) VALUES
(1, 'Thomas Richelieu Sr', 'particulier', 'Fass Paillote', '778165341', '', 9),
(2, 'ATTAKPA Cornelis', 'particulier', 'Bene Tally', '781033744', '', 12),
(3, 'Tossou', 'particulier', '38 Quai du rhin', '0648857850', '', 13);

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `id` int(11) NOT NULL,
  `type` varchar(40) NOT NULL,
  `date` date NOT NULL,
  `statut` varchar(40) NOT NULL,
  `prix` double DEFAULT NULL,
  `id_client` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`id`, `type`, `date`, `statut`, `prix`, `id_client`) VALUES
(4, 'Livraison', '2023-03-23', 'En cours', 1240, 1),
(5, 'Livraison', '2023-03-24', 'En cours', 146, 1),
(6, 'Livraison', '2023-03-24', 'En cours', 50, 1),
(7, 'Livraison', '2023-03-24', 'En cours', 279, 1),
(8, 'Livraison', '2023-03-24', 'En cours', 228, 1),
(9, 'Livraison', '2023-03-25', 'En cours', 250, 2);

-- --------------------------------------------------------

--
-- Structure de la table `fournisseur`
--

CREATE TABLE `fournisseur` (
  `id` int(11) NOT NULL,
  `code` varchar(40) DEFAULT NULL,
  `nom` varchar(40) NOT NULL,
  `adresse` varchar(40) NOT NULL,
  `nationalite` varchar(40) NOT NULL,
  `telephone` varchar(40) NOT NULL,
  `id_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `fournisseur`
--

INSERT INTO `fournisseur` (`id`, `code`, `nom`, `adresse`, `nationalite`, `telephone`, `id_user`) VALUES
(4, 'F0001', 'MEDENOU', 'Fass Paillote', 'bÃ©ninois', '778165341', 7),
(5, 'F0005', 'DIOP Mamadou Thiam', 'Medina', 'sÃ©nÃ©galais', '761747456', 10),
(6, 'F0006', 'MAIGA Ramadan', 'HLM 5', 'malien', '788889895', 11);

-- --------------------------------------------------------

--
-- Structure de la table `livraison`
--

CREATE TABLE `livraison` (
  `id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `adresse` varchar(50) DEFAULT NULL,
  `telephone` varchar(40) DEFAULT NULL,
  `etat` varchar(40) DEFAULT 'Initiée',
  `id_commande` int(11) NOT NULL,
  `id_fournisseur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `livraison`
--

INSERT INTO `livraison` (`id`, `date`, `adresse`, `telephone`, `etat`, `id_commande`, `id_fournisseur`) VALUES
(2, NULL, 'Fass Paillote', '778165341', 'Initiée', 4, 4),
(3, NULL, 'Fass Paillote', '778165341', 'Initiée', 5, 4),
(4, NULL, 'Fass Paillote', '778165341', 'Initiée', 5, 5),
(5, NULL, 'Fass Paillote', '778165341', 'Initiée', 6, 5),
(6, NULL, 'Fass Paillote', '778165341', 'Initiée', 6, 4),
(7, NULL, 'Fass Paillote', '778165341', 'Initiée', 7, 4),
(8, NULL, 'Fass Paillote', '778165341', 'Initiée', 7, 6),
(9, NULL, 'Fass Paillote', '778165341', 'Initiée', 7, 5),
(10, NULL, 'Fass Paillote', '778165341', 'Initiée', 8, 6),
(11, NULL, 'Fass Paillote', '778165341', 'Initiée', 8, 4),
(12, NULL, 'Fass Paillote', '778165341', 'Initiée', 8, 5),
(13, NULL, 'Bene Tally', '781033744', 'Initiée', 9, 5),
(14, NULL, 'Bene Tally', '781033744', 'Initiée', 9, 4);

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

CREATE TABLE `panier` (
  `id` int(11) NOT NULL,
  `prix` int(11) NOT NULL,
  `id_client` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`id`, `prix`, `id_client`) VALUES
(1, 0, 1),
(2, 230, 2),
(3, 0, 3);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` int(11) NOT NULL,
  `nom` varchar(40) NOT NULL,
  `image` varchar(40) NOT NULL,
  `quantite` double NOT NULL,
  `prixUnitaire` double NOT NULL,
  `dateFab` date DEFAULT NULL,
  `dateExp` date DEFAULT NULL,
  `id_categorie` int(11) NOT NULL,
  `etat` char(3) NOT NULL DEFAULT 'VAL',
  `id_fournisseur` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `nom`, `image`, `quantite`, `prixUnitaire`, `dateFab`, `dateExp`, `id_categorie`, `etat`, `id_fournisseur`) VALUES
(2, 'Chaise blanche', 'F0001chaise.jpg', 18, 10, NULL, NULL, 1, 'VAL', 4),
(4, 'Tomate en boîte', 'F00012023-03-22-tomateEnBoite.jpg', 195, 12, '2023-03-02', '2025-10-30', 2, 'VAL', 4),
(5, 'Coco Channel Noir.', 'F00052023-03-24-cocoChannelNoir.jpg', 30, 30, NULL, NULL, 3, 'VAL', 5),
(6, 'Creme corporelle blanche', 'F00052023-03-24-cremeCorporelle.jpg', 95, 6, NULL, NULL, 3, 'VAL', 5),
(7, 'Chaussures Nike', 'F00012023-03-24-nikeShoes.jpg', 28, 100, NULL, NULL, 4, 'VAL', 4),
(8, 'Sac à main', 'F00062023-03-24-sacAmain.jpg', 10, 200, NULL, NULL, 4, 'VAL', 6),
(10, 'Talons paillettes', 'F00062023-03-24-02-34-talonsaiguille.jpg', 10, 35, NULL, NULL, 4, 'VAL', 6),
(11, 'Bracelet argent', 'F00062023-03-24-02-36-bracelet.jpg', 19, 15, NULL, NULL, 4, 'VAL', 6),
(12, 'Nike air', 'F00052023-03-24-02-40-nikeAir.jpg', 19, 80, NULL, NULL, 4, 'VAL', 5),
(13, 'Baskets', 'F00052023-03-24-16-35-basket.jpg', 16, 50, NULL, NULL, 4, 'VAL', 5),
(14, 'Pendentif', 'F00062023-03-24-16-55-pendentif.jpg', 26, 19, NULL, NULL, 4, 'VAL', 6);

-- --------------------------------------------------------

--
-- Structure de la table `produitlivraison`
--

CREATE TABLE `produitlivraison` (
  `id` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `quantite` int(11) NOT NULL,
  `id_livraison` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produitlivraison`
--

INSERT INTO `produitlivraison` (`id`, `id_produit`, `quantite`, `id_livraison`) VALUES
(1, 4, 10, 2),
(2, 2, 12, 2),
(3, 4, 3, 3),
(4, 5, 3, 4),
(5, 6, 4, 5),
(6, 2, 1, 6),
(7, 4, 2, 7),
(8, 11, 3, 8),
(9, 5, 1, 9),
(10, 13, 3, 9),
(11, 14, 3, 10),
(12, 11, 1, 10),
(13, 2, 2, 11),
(14, 7, 1, 11),
(15, 6, 1, 12),
(16, 12, 1, 13),
(17, 13, 1, 13),
(18, 7, 1, 14);

-- --------------------------------------------------------

--
-- Structure de la table `produitpanier`
--

CREATE TABLE `produitpanier` (
  `id` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `quantite` int(11) NOT NULL,
  `id_panier` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `produitpanier`
--

INSERT INTO `produitpanier` (`id`, `id_produit`, `quantite`, `id_panier`) VALUES
(30, 2, 1, 3),
(31, 7, 1, 3);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL,
  `login` varchar(40) NOT NULL,
  `password` varchar(100) NOT NULL,
  `profil` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `login`, `password`, `profil`) VALUES
(7, 'richarddarylmedenou@gmail.com', '9bce8bfbbbd103005bec3a49ca44193f9e9575320f7066a87a939d0fad64ff6f', 'Fournisseur'),
(9, 'thomas.richelieu@gmail.com', 'd38681074467c0bc147b17a9a12b9efa8cc10bcf545f5b0bccccf5a93c4a2b79', 'Client'),
(10, 'diop.mamadou@gmail.com', '4f968c54dfd1600c590e586f6ef9fa64f8070feef82e34310c244a6346f3cd34', 'Fournisseur'),
(11, 'mkatia@gmail.com', '1ea3eb87cea7c79031d0af546d0c494d6499b1f7f2ce7af778081fb61f87482e', 'Fournisseur'),
(12, 'attakpacornelis@gmail.com', 'e84ab959e8da1f90dc5176476986409ff427fb76c3da36ba391d9ba3bf2f0f7d', 'Client'),
(13, 'anhe.otntioni@gmail.com', 'f1f388284eee8a321f3b33a4e2459a6934c49400a62204028d1966ae93f1498d', 'Client');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_client` (`id_client`);

--
-- Index pour la table `fournisseur`
--
ALTER TABLE `fournisseur`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`);

--
-- Index pour la table `livraison`
--
ALTER TABLE `livraison`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_commande` (`id_commande`),
  ADD KEY `id_fournisseur` (`id_fournisseur`);

--
-- Index pour la table `panier`
--
ALTER TABLE `panier`
  ADD PRIMARY KEY (`id`),
  ADD KEY `CLIENT` (`id_client`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categorie` (`id_categorie`),
  ADD KEY `id_fournisseur` (`id_fournisseur`);

--
-- Index pour la table `produitlivraison`
--
ALTER TABLE `produitlivraison`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_livraison` (`id_livraison`);

--
-- Index pour la table `produitpanier`
--
ALTER TABLE `produitpanier`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_produit` (`id_produit`),
  ADD KEY `id_panier` (`id_panier`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `fournisseur`
--
ALTER TABLE `fournisseur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `livraison`
--
ALTER TABLE `livraison`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `panier`
--
ALTER TABLE `panier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `produitlivraison`
--
ALTER TABLE `produitlivraison`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT pour la table `produitpanier`
--
ALTER TABLE `produitpanier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `UTILISATEUR_CLIENT` FOREIGN KEY (`id_user`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `client_id` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `fournisseur`
--
ALTER TABLE `fournisseur`
  ADD CONSTRAINT `UTILISATEUR_FOURNISS` FOREIGN KEY (`id_user`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `livraison`
--
ALTER TABLE `livraison`
  ADD CONSTRAINT `COMMANDE` FOREIGN KEY (`id_commande`) REFERENCES `commande` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FOURNISSEUR_ID` FOREIGN KEY (`id_fournisseur`) REFERENCES `fournisseur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `panier`
--
ALTER TABLE `panier`
  ADD CONSTRAINT `CLIENT` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `CATEGORIE_PRODUIT` FOREIGN KEY (`id_categorie`) REFERENCES `categorie` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FOURNISSEUR` FOREIGN KEY (`id_fournisseur`) REFERENCES `fournisseur` (`id`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `produitlivraison`
--
ALTER TABLE `produitlivraison`
  ADD CONSTRAINT `LIVRAISON_ID` FOREIGN KEY (`id_livraison`) REFERENCES `livraison` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `produitpanier`
--
ALTER TABLE `produitpanier`
  ADD CONSTRAINT `PANIER` FOREIGN KEY (`id_panier`) REFERENCES `panier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `PRODUIT_ID` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

import pyray as pr
import numpy as np
from pyray import Vector3
import trimesh


def euler_characteristic(mesh):
    """Calcule la caractéristique d'Euler-Poincaré pour un maillage."""
    V = len(mesh.vertices) 
    E = len(mesh.edges_unique)     
    F = len(mesh.faces)  
    
    chi = V - E + F
    return chi
def genus_from_euler(chi):
    """Calcule le genre d'une surface à partir de sa caractéristique d'Euler."""
    return (2 - chi) / 2
def load_ply_file(file_path):
    """Charge un fichier PLY et retourne le mesh en tant que structure de données trimesh."""
    mesh = trimesh.load(file_path)
    return mesh
def main():
    mesh = load_ply_file('earth.ply')
    X = euler_characteristic(mesh)
    print("X(M) = {}".format(X))
    print("Genus = {}".format(genus_from_euler(X)))
if __name__ == "__main__":
    main()
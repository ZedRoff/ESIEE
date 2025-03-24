import pyray as pr
import numpy as np
from pyray import Vector3
from exo1_tp1_copy import cross_product, vector_length, vector_normalize
import trimesh


def initialize_camera():
    """Initialise la caméra 3D."""
    return pr.Camera3D(
        Vector3(0, 10, 10),  # Position
        Vector3(0, 0, 0),    # Target
        Vector3(0, 1, 0),    # Up
        45,                  # FOV
        pr.CAMERA_PERSPECTIVE
    )

def update_camera_position(camera, movement_speed):
    """Met à jour la position de la caméra en fonction des touches pressées."""
    if pr.is_key_down(pr.KEY_W):
        camera.position.z -= movement_speed
    if pr.is_key_down(pr.KEY_S):
        camera.position.z += movement_speed
    if pr.is_key_down(pr.KEY_A):
        camera.position.x -= movement_speed
    if pr.is_key_down(pr.KEY_D):
        camera.position.x += movement_speed
    if pr.is_key_down(pr.KEY_Q):
        camera.position.y += movement_speed
    if pr.is_key_down(pr.KEY_E):
        camera.position.y -= movement_speed
        

def generate_random_points_on_plane(point, normal, num_points=10, spread=5, deviation=5):
    """
    # Génère des points aléatoires situés sur un plan défini par un point et un vecteur normal.

    Args:
        point (Vector3): Un point sur le plan.
        normal (Vector3): Le vecteur normal définissant le plan.
        num_points (int): Le nombre de points à générer.
        spread (float): L'écart maximal des points par rapport au point de référence.

    Returns:
        list: Une liste de points Vector3 situés sur le plan.
    """

    normal = vector_normalize(normal)

    # Générer deux vecteurs orthogonaux dans le plan
    if abs(normal.x) > 1e-3 or abs(normal.z) > 1e-3:
        u = vector_normalize(Vector3(-normal.z, 0, normal.x))
    else:
        u = vector_normalize(Vector3(0, -normal.z, normal.y))
    v = vector_normalize(cross_product(normal, u))

    # Générer des points aléatoires dans le plan en utilisant la base orthogonale
    points = []
    for _ in range(num_points):
        r1 = np.random.uniform(-spread, spread)
        r2 = np.random.uniform(-spread, spread)
        random_deviation = np.random.uniform(-deviation, deviation)
        p = Vector3(
            point.x + r1 * u.x + r2 * v.x + normal.x * random_deviation,
            point.y + r1 * u.y + r2 * v.y + normal.y * random_deviation,
            point.z + r1 * u.z + r2 * v.z + normal.z * random_deviation
        )
        points.append(p)

    return points




def draw_points(points, color):
    """Dessiner le point 3Ds."""
    for point in points:
        pr.draw_sphere(point, 0.1, color)  

def calc_pmin_pmax(points):
    min_x = float('inf')
    min_y = float('inf')
    min_z = float('inf')
    
    max_x = float('-inf')
    max_y = float('-inf')
    max_z = float('-inf')
    
    
    for p in points:
        if p.x < min_x:
            min_x = p.x
        if p.y < min_y:
            min_y = p.y
        if p.z < min_z:
            min_z = p.z
        if p.x > max_x:
            max_x = p.x
        if p.y > max_y:
            max_y = p.y
        if p.z > max_z:
            max_z = p.z
    pmin = Vector3(min_x, min_y, min_z)
    pmax = Vector3(max_x,max_y,max_z)
    return pmin, pmax
def apply_transformation(points, matrix):
    transformed_points = []
    for p in points:
        vec = np.array([p.x, p.y, p.z, 1])
        transformed_vec = matrix @ vec
        transformed_points.append(Vector3(transformed_vec[0], transformed_vec[1], transformed_vec[2]))
    return transformed_points
def transform_aabb(pmin, pmax, matrix):
    corners = [
        Vector3(pmin.x, pmin.y, pmin.z),
    Vector3(pmin.x, pmin.y, pmax.z),
    Vector3(pmax.x, pmin.y, pmax.z),
    Vector3(pmax.x, pmin.y, pmin.z),
    Vector3(pmin.x, pmax.y, pmin.z),
    Vector3(pmin.x, pmax.y, pmax.z),
    Vector3(pmax.x, pmax.y, pmax.z),
    Vector3(pmax.x, pmax.y, pmin.z)
    ]
    transformed_corners = apply_transformation(corners, matrix)
    return calc_pmin_pmax(transformed_corners)

def draw_aabb(pmin, pmax, color):
    A = Vector3(pmin.x, pmin.y, pmin.z)
    B = Vector3(pmin.x, pmin.y, pmax.z)
    C = Vector3(pmax.x, pmin.y, pmax.z)
    D = Vector3(pmax.x, pmin.y, pmin.z)
    E = Vector3(pmin.x, pmax.y, pmin.z)
    F = Vector3(pmin.x, pmax.y, pmax.z)
    G = Vector3(pmax.x, pmax.y, pmax.z)
    H = Vector3(pmax.x, pmax.y, pmin.z)
    pr.draw_line_3d(A, B, color)
    pr.draw_line_3d(B, C, color)
    pr.draw_line_3d(C, D, color)
    pr.draw_line_3d(D, A, color)

    
    pr.draw_line_3d(E, F, color)
    pr.draw_line_3d(F, G, color)
    pr.draw_line_3d(G, H, color)
    pr.draw_line_3d(H, E, color)
    
    pr.draw_line_3d(A, E, color)
    pr.draw_line_3d(B, F, color)
    pr.draw_line_3d(C, G, color)
    pr.draw_line_3d(D, H, color)
   
def rotation_matrix_x(angle):
    """Crée une matrice homogène de rotation autour de X."""
    cos_a, sin_a = np.cos(angle), np.sin(angle)
    return np.array([
        [1, 0, 0, 0],
        [0, cos_a, -sin_a, 0],
        [0, sin_a, cos_a, 0],
        [0, 0, 0, 1]
    ])

def draw_mesh(mesh):
    """Dessine le mesh complet avec sommets, arêtes et faces."""
    for face in mesh.faces:
        v0 = Vector3(*mesh.vertices[face[0]])
        v1 = Vector3(*mesh.vertices[face[1]])
        v2 = Vector3(*mesh.vertices[face[2]])
        pr.draw_triangle_3d(v0, v1, v2, pr.LIGHTGRAY)
    
    for edge in mesh.edges:
        v_start = Vector3(*mesh.vertices[edge[0]])
        v_end = Vector3(*mesh.vertices[edge[1]])
        pr.draw_line_3d(v_start, v_end, pr.BLACK)
    
    for vertex in mesh.vertices:
        pr.draw_sphere(Vector3(*vertex), 0.05, pr.RED)

def load_ply_file(file_path):
    """Charge un fichier PLY et retourne le mesh en tant que structure de données trimesh."""
    mesh = trimesh.load(file_path)
    return mesh
def initialize_mesh_for_transforming(mesh):
    """Stocke les sommets originaux du mesh pour permettre un redimensionnement dynamique."""
    mesh.original_vertices = np.copy(mesh.vertices)

def main():
    pr.init_window(1000, 800, "Visualisation de points et de plans en 3D")
    pr.set_target_fps(60)
    movement_speed = 0.1
    camera = initialize_camera()
    ply_file_path = "dolphin.ply"
    mesh = load_ply_file(ply_file_path)
    
    initialize_mesh_for_transforming(mesh)
    # Angle initial pour le slider
    angle_ptr = pr.ffi.new('float *', 0.0)

    # Générer des points aléatoires situés sur un plan
    points = generate_random_points_on_plane(Vector3(0, 0, 0), Vector3(1, 1, 1), num_points=25, spread=5)

    while not pr.window_should_close():
        update_camera_position(camera, movement_speed)

        # Mettre à jour l'angle en fonction du slider
        angle_radians = np.radians(angle_ptr[0])

        # Appliquer la transformation à chaque itération
        rotation_matrix = rotation_matrix_x(angle_radians)

        # Transformer l'AABB
        pmin, pmax = calc_pmin_pmax([Vector3(*v) for v in mesh.vertices])
        pmin_trans, pmax_trans = transform_aabb(pmin, pmax, rotation_matrix)

        pr.begin_drawing()
        pr.clear_background(pr.RAYWHITE)

        pr.begin_mode_3d(camera)

        # Dessiner les points et AABB avant et après transformation
        draw_aabb(pmin, pmax, pr.RED)
        draw_mesh(mesh)
        draw_aabb(pmin_trans, pmax_trans, pr.BLUE)
        mesh.vertices = np.array([[v.x, v.y, v.z] for v in apply_transformation([Vector3(*v) for v in mesh.original_vertices], rotation_matrix)])

        draw_mesh(mesh)

        pr.end_mode_3d()

        # Interface utilisateur
        pr.draw_text("Utilisez WASD pour déplacer la caméra", 10, 10, 20, pr.DARKGRAY)
        pr.draw_text("Angle de rotation", 10, 580, 20, pr.BLACK)
        pr.gui_slider_bar(pr.Rectangle(10, 620, 200, 20), "0.0", "45.0", angle_ptr, 0, 45.0)

        pr.end_drawing()

    pr.close_window()

if __name__ == "__main__":
    main()
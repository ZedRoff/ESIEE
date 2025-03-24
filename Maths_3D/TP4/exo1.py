import pyray as pr
import numpy as np
from pyray import Vector3
from exo1_tp1_copy import cross_product, vector_length, vector_normalize



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
        

def generate_random_points_on_plane(point, normal, num_points=10, spread=5, deviation=1):
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


def compute_normal(points):
    """
    TODO
    """
    n = Vector3(0,0,0)
    for i in range(len(points)-1):
        pi = points[i]
        pip1 = points[i+1]
        n.x += (pi.z + pip1.z) * (pi.y - pip1.y)
        n.y += (pi.x + pip1.x) * (pi.z - pip1.z)
        n.z += (pi.y + pip1.y) * (pi.x - pip1.x)

    return vector_normalize(n)



def draw_points(points):
    """Dessiner le point 3Ds."""
    for point in points:
        pr.draw_sphere(point, 0.1, pr.RED)  


def draw_plane(normal, point_on_plane, color, size=10):
    """
   TODO
    """
    normal = vector_normalize(normal)
    # Générer deux vecteurs orthogonaux dans le plan
    if abs(normal.x) > 1e-3 or abs(normal.z) > 1e-3:
        v1 = vector_normalize(Vector3(-normal.z, 0, normal.x))
    else:
        v1 = vector_normalize(Vector3(0, -normal.z, normal.y))
    v2 = vector_normalize(cross_product(normal, v1))

    for i in range(-size, size + 1):
        
        start1 = Vector3(point_on_plane.x + v1.x * -size + v2.x * i, 
                         point_on_plane.y + v1.y * -size + v2.y * i, 
                         point_on_plane.z + v1.z * -size + v2.z * i)
        end1 = Vector3(point_on_plane.x + v1.x * size + v2.x * i,  
                       point_on_plane.y + v1.y * size + v2.y * i,
                       point_on_plane.z + v1.z * size + v2.z * i)
        start2 = Vector3(point_on_plane.x + v2.x * -size + v1.x * i,  
                         point_on_plane.y + v2.y * -size + v1.y * i, 
                         point_on_plane.z +v2.z * -size + v1.z * i)
        end2 = Vector3(point_on_plane.x + v2.x * size + v1.x * i,  
                       point_on_plane.y + v2.y * size + v1.y * i, 
                       point_on_plane.z +v2.z * size + v1.z * i)
        
        pr.draw_line_3d(start1, end1, color)
        pr.draw_line_3d(start2, end2, color)
def draw_vector_3(start, end, color, thickness=0.05):
    """Nous dessinons un vecteur en utilisant un cylindre et un cône."""
    direction = Vector3(end.x - start.x, end.y - start.y, end.z - start.z)
    length = vector_length(direction)
    head_size = length * 0.8
    
    n_direction = vector_normalize(direction)
    
    arrow_start = Vector3(start.x + n_direction.x * head_size, 
                          start.y + n_direction.y * head_size, 
                          start.z + n_direction.z * head_size)
    
    pr.draw_cylinder_ex(start, end, thickness / 2, thickness / 2, 8, color)
    pr.draw_cylinder_ex(arrow_start, end, thickness * 2, thickness / 5, 8, color)


def main():
    pr.init_window(1000, 800, "Visualisation de points et de plans en 3D")
    pr.set_target_fps(60)
    movement_speed = 0.1
    camera = initialize_camera()
    
    # Générer des points aléatoires situés sur le plan
    points = generate_random_points_on_plane(Vector3(0, 0, 0), Vector3(1, 1, 1), num_points=500, spread=5)
    
    # TODO: Calculer le vecteur normal du plan à partir des points
    normal = compute_normal(points)
    normal_ref = vector_normalize(Vector3(1,1,1))
    
    # TODO: La normale doit être affichée au centroïde du plan
    moy_x = 0
    moy_y = 0
    moy_z = 0
    length = len(points)
    for i in range(length):
        moy_x += points[i].x
        moy_y += points[i].y
        moy_z += points[i].z

    moy_x /= length 
    moy_y /= length 
    moy_z /= length
    centroid = Vector3(
            moy_x,
            moy_y,
            moy_z
    )
    point_on_plane = Vector3(centroid.x, centroid.y, centroid.z)
    point_on_plane_ref = Vector3(0,0,0)
    
    while not pr.window_should_close():
        update_camera_position(camera, movement_speed)

        pr.begin_drawing()
        pr.clear_background(pr.RAYWHITE)

        pr.begin_mode_3d(camera)
        
        draw_points(points)
        draw_plane(normal, point_on_plane, pr.RED)
        
        draw_vector_3(point_on_plane, Vector3(centroid.x + normal.x * 5, centroid.y + normal.y * 5, centroid.z + normal.z * 5), pr.RED)
        draw_plane(normal_ref, point_on_plane_ref, pr.GREEN)
        
        draw_vector_3(point_on_plane_ref, Vector3(point_on_plane_ref.x + normal_ref.x * 5, point_on_plane_ref.y + normal_ref.y * 5, point_on_plane_ref.z + normal_ref.z * 5), pr.GREEN)
        pr.end_mode_3d()
        pr.draw_text("Utilisez WASD pour déplacer la caméra", 10, 10, 20, pr.DARKGRAY)
        pr.end_drawing()

    pr.close_window()
    
if __name__ == "__main__":
    main()
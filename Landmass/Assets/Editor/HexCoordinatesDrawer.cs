using UnityEditor;
using UnityEngine;

[CustomPropertyDrawer(typeof(HexCoordinates))]
public class HexCoordinatesDrawer : PropertyDrawer {

    public override void OnGUI(Rect position, SerializedProperty property, GUIContent label)
    {
        // get values
        HexCoordinates coordinates = new HexCoordinates(property.FindPropertyRelative("x").intValue, property.FindPropertyRelative("z").intValue);
        // get position and spacing
        position = EditorGUI.PrefixLabel(position, label);
        // draw content
        GUI.Label(position, coordinates.ToString());
    }
}
